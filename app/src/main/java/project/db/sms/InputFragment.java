package project.db.sms;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {

    private EditText originIn;
    private EditText destIn;

    public InputSectionListener activityCommander;

    public interface InputSectionListener {
        public void getRoute(String origin, String Destination);
        public void resetFields();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            activityCommander = (InputSectionListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.input_fragment, container, false);

        originIn = (EditText) view.findViewById(R.id.originInput);
        destIn = (EditText) view.findViewById(R.id.destinationInput);
        Button searchBtn = (Button) view.findViewById(R.id.searchButton);
        Button resetBtn = (Button) view.findViewById(R.id.resetButton);

        searchBtn.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        searchButtonClicked(v);
                    }
                }
        );

        resetBtn.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        resetButtonClicked(v);
                    }
                }
        );

        return view;
    }

    // Calls this when search button is clicked
    public void searchButtonClicked(View v){
        activityCommander.getRoute(originIn.getText().toString(), destIn.getText().toString());
    }

    // Calls this when reset button is clicked
    public void resetButtonClicked(View v){
    }


}
