package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alome.tellerium.Activities.Login;
import com.alome.tellerium.Adapters.mainAdapter;
import com.alome.tellerium.Models.mainModel;
import com.alome.tellerium.R;
import com.alome.tellerium.Utils.Helper;
import com.alome.tellerium.Utils.constants;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmail.samehadar.iosdialog.IOSDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dashboard extends Fragment implements PopupMenu.OnMenuItemClickListener {
    mainAdapter adapter;
    Helper helper;
    ArrayList<mainModel> arrayList=new ArrayList<>();
    RecyclerView recyclerView;
    TextView amt;
    ImageView menu;
    View view;
    PopupMenu.OnMenuItemClickListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_fragment, container, false);
        initUI();
        loadData();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), menu);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });
        mainModel model=new mainModel(20,"Farmers", R.drawable.ic_farmer);
        arrayList.add(model);
        model=new mainModel(10,"Farms", R.drawable.ic_sprout);
        arrayList.add(model);
        recyclerView.setAdapter(adapter);
        amt.setText(Helper.Utils.getCurrencySymbol("NGN")+"100,000");
        return view;
    }

    private void initUI() {

        menu=view.findViewById(R.id.menu);
        helper=new Helper(getContext());
        amt=view.findViewById(R.id.amt);
        recyclerView=view.findViewById(R.id.recyclerView);
        adapter=new mainAdapter(getContext(), arrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id=menuItem.getItemId();
        switch (id){
            case R.id.log_out:
                final IOSDialog dialog= new IOSDialog.Builder(getActivity())
                        .setMessageContent("Please wait...")
                        .setCancelable(false)
                        .show();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        helper.logout();
                        startActivity(new Intent(getContext(), Login.class));
                        getActivity().finish();
                    }
                }, 2000);

                break;
        }
        return true;
    }


    public void loadData(){
        final IOSDialog iosDialog= new IOSDialog.Builder(getContext())
                .setMessageContent("Please wait..")
                .setCancelable(false)
                .show();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        final StringRequest getRequest = new StringRequest(Request.Method.GET, constants.BASE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        iosDialog.dismiss();
                        // response
                        if (response!=null){
                            Toast.makeText(getContext(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d("Response", response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error =>" +error.toString());
                    }
                }
        );
        queue.add(getRequest);
    }
}
