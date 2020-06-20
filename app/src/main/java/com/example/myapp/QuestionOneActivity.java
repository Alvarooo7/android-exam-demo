package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class QuestionOneActivity extends AppCompatActivity {

    EditText txtSueldo,txtNombre,txtHoras;
    TextView txtResult;
    Button btnCalcular;
    Spinner spType;
    RadioGroup rbPension,rbSeguro;
    RadioButton rbChoice;

    double salaryFinal,aditionalAmount,pensionAmount,insuranceAmount,igvAmount;
    int extraHours;
    String name,salaryBase,hours,typeEmployee,pensionText="",insuranceText="",pensionSelect,seguroSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_one);
        initView();
        addListener();
    }

    public void initView(){
        spType = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> typesAdapter = ArrayAdapter.createFromResource(this,
                R.array.opciones,
                R.layout.support_simple_spinner_dropdown_item);
        spType.setAdapter(typesAdapter);
     /*   spType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });*/
        rbPension = findViewById(R.id.rbPension);
        rbSeguro = findViewById(R.id.rbSeguro);
        txtNombre = findViewById(R.id.txtNombre);
        txtSueldo = findViewById(R.id.txtSueldo);
        txtHoras = findViewById(R.id.txtHoras);
        btnCalcular = findViewById(R.id.btnCalculate);

        txtResult = findViewById(R.id.txtResult);

    }

    public void addListener(){
        btnCalcular.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getItemsValues();
                if (!validateInputs()) return;
                calculateSalary();
                showSummary();
            }
        });
    }


    public void calculateSalary(){
        double salaryInit = Double.parseDouble(salaryBase);
        salaryFinal = salaryInit;
        if (typeEmployee.equals("PLANILLA")) {salaryFinal += aditionalAmount = 1000; }
        else salaryFinal = salaryFinal +( aditionalAmount = (salaryFinal * 0.05));

        if (typeEmployee.equals("PLANILLA")) salaryFinal += extraHours = (Integer.parseInt(hours) *50);
        else salaryFinal += extraHours = (Integer.parseInt(hours) *30);

        switch (pensionSelect){
            case "AFP" : salaryFinal = salaryFinal - (pensionAmount = (salaryInit * 0.14));pensionText="AFP(14%): "+pensionAmount+"\n"; break;
            case "ONP" : salaryFinal = salaryFinal - (pensionAmount = (salaryInit * 0.12));pensionText="ONP(12%): "+pensionAmount+"\n"; break;
        }

        switch (seguroSelect){
            case "EPS" : salaryFinal -= insuranceAmount = 30;insuranceText ="EPS: "+ insuranceAmount+"\n"; break;
            case "Seguro Oncológico" : salaryFinal -= insuranceAmount = 50;insuranceText ="Seguro Oncológico: "+ insuranceAmount+"\n"; break;
            case "Afiliación Club" : salaryFinal -= insuranceAmount = 80;insuranceText ="Afiliación Club: "+ insuranceAmount+"\n"; break;
        }

        salaryFinal -= igvAmount = (salaryFinal * 0.08);
    }
    public void showSummary(){
        double grossSalary= Double.parseDouble(salaryBase) + extraHours + aditionalAmount;
        txtResult.setText(
                "Nombre: "+name+"\n"+
                "Tipo de Trabajador: "+typeEmployee+" le corresponde Bono: "+ aditionalAmount +"\n"+
                "Sueldo Básico: "+salaryBase+"\n"+
                "Horas Extra: "+extraHours+"\n"+
                "Sueldo Bruto: "+grossSalary+"\n"+
                 pensionText+
                 insuranceText+
                 "IGV: "+igvAmount+"\n"+
                 "SUELDO NETO: "+salaryFinal
                );
    }

    public void getItemsValues(){
        int itemId = rbPension.getCheckedRadioButtonId();
        rbChoice = findViewById(itemId);
        try {
            pensionSelect = rbChoice.getText().toString();
        }catch (Exception exp){
            pensionSelect="";
        }


        itemId = rbSeguro.getCheckedRadioButtonId();
        rbChoice = findViewById(itemId);
        try {
            seguroSelect = rbChoice.getText().toString();
        }catch (Exception exp){
            seguroSelect="";
        }


        name = txtNombre.getText().toString();
        salaryBase = txtSueldo.getText().toString();
        hours = txtHoras.getText().toString();
        typeEmployee = spType.getSelectedItem().toString();
    }

    public boolean validateInputs(){
        try{
            if (name.isEmpty()) throw new Exception();

            double salaryVal = Double.parseDouble(salaryBase);
            int hoursVal = Integer.parseInt(hours);
            if(salaryVal<1)
                throw new Exception();

            if(hoursVal<0 && hoursVal>18)
                throw new Exception();

            if (typeEmployee.equals("PLANILLA") && pensionSelect == "")
                    throw new Exception();

            return true;
        }catch (Exception exp){
            cleanTextView();
            Toast.makeText(this,"Ingrese Correctamente todos los campos",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void cleanTextView(){
        txtResult.setText("=======Resumen========");
    }

}