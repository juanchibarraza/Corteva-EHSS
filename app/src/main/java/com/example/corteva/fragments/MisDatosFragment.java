package com.example.corteva.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.corteva.ControladorConfig;
import com.example.corteva.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MisDatosFragment extends Fragment {

    LinearLayout LL;
    ScrollView scroll;
    Spinner spinnerFecha, spinnerTipo;
    private BarChart barChart;
    TextView barChartTitle;
    private ListView lv1;
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_misdatos, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        //Recibo parametros
        Bundle bundle=this.getArguments();
        email = bundle.getString("email");
        scroll = getActivity().findViewById(R.id.Scroll);
        LL = getActivity().findViewById(R.id.LL);
        lv1=getActivity().findViewById(R.id.lv1);

        lv1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scroll.requestDisallowInterceptTouchEvent(true);
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_UP:
                        scroll.requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        final FloatingActionButton btnShowHide = getActivity().findViewById(R.id.showHide);
        btnShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Primer sincronizacion
                btnShowHide.hide();
                LL.setVisibility(View.VISIBLE);
            }
        });
        spinnerTipo=getActivity().findViewById(R.id.spinnerTipo);
        spinnerFecha=getActivity().findViewById(R.id.spinnerFecha);
        barChart = (BarChart)getActivity().findViewById(R.id.barChart);
        barChartTitle = getActivity().findViewById(R.id.barChartTitle);
        //String [] tipos=new String[]{"Todos","BBP/Stop","Momentos de Seguridad","Pruebas a campo","Reportes de Incidentes","TopTen","UAC"};
        //String [] tipos=new String[]{"Todos","SODA","Momentos de Seguridad","Pruebas a campo","Reportes de Incidentes","TopTen","UAC"};
        String [] tipos=new String[]{"Todos","Condiciones inseguras","Pausas de Seguridad","Pruebas a campo","Reportes de Desvíos","SODA","TopTen"};
        ArrayAdapter<String> adapterTipos=new ArrayAdapter<>(getActivity(),R.layout.spinner_item_reportes,tipos);
        spinnerTipo.setAdapter(adapterTipos);

        String [] fechas=new String[]{"Éste mes","Últimos 3 meses","Último año"};
        ArrayAdapter<String> adapterFechas=new ArrayAdapter<>(getActivity(),R.layout.spinner_item_reportes,fechas);
        spinnerFecha.setAdapter(adapterFechas);

        //BOTON BUSCAR
        Button botonBuscar=view.findViewById(R.id.buttonBuscar);
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btnShowHide.setVisibility(View.GONE);
                LL.setVisibility(View.GONE);
                btnShowHide.show();
                readRecords();
            }
        });
    }

    public void readRecords(){
        String selectedFecha = spinnerFecha.getSelectedItem().toString();
        final String selectedTipo = spinnerTipo.getSelectedItem().toString();

        ArrayList<BarEntry> datos = new ControladorConfig(getContext()).Get_MisCargas(selectedFecha,selectedTipo);

        barChart.animateY(5000);
        BarDataSet barDataSet = new BarDataSet(datos, "Reportes");
        barDataSet.setBarBorderWidth(0);
        barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        barDataSet.setValueTextSize(12f);
        barChart.getDescription().setEnabled(false); //Hide title
        barChart.getLegend().setEnabled(false);   // Hide the legend
        XAxis xAxis = barChart.getXAxis();
        String[] months;
        if (selectedTipo=="Todos"){
            //months = new String[]{"BBP/Stop","Momentos de Seguridad","Pruebas a campo","Reportes de Incidentes","TopTen","UAC"};
            months = new String[]{"SODA","Pausas de Seguridad","Pruebas a campo","Reportes de Desvíos","TopTen","Condiciones Inseguras"};
            barChartTitle.setText("Cargas de " + selectedFecha);
            xAxis.setLabelRotationAngle(-60);
        }else{
            months = new String[]{selectedTipo};
            barChartTitle.setText(selectedTipo + "\n" + selectedFecha);
            xAxis.setLabelRotationAngle(0);
        }
        xAxis.setAxisMinimum(-1);
        xAxis.setAxisMaximum(6);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months) {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (selectedTipo=="Todos"){
                    return super.getFormattedValue(value + 1f, axis);
                }else{
                    return super.getFormattedValue(value, axis);
                }
            }
        });
        xAxis.setCenterAxisLabels(true);

        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setEnabled(false);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barChart.setFitBars(true);
        //Remueve el espacio blanco de abajo
        barChart.setExtraOffsets(0,0,0,30);
        barChart.animateXY(5000, 5000);
        barChart.invalidate();

        ArrayList<HashMap<String, String>> filas= new ControladorConfig(getContext()).Get_MisCargasDetalle(selectedFecha,selectedTipo,email);
        ListAdapter adapter = new SimpleAdapter(
                getContext(), filas,
                R.layout.list_view_personalizado,
                new String[]{"", "tipo", "fechahora", "datos"},
                new int[]{R.id.ID, R.id.titulo1, R.id.titulo2, R.id.titulo3}){
        };
        lv1.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
    }
}