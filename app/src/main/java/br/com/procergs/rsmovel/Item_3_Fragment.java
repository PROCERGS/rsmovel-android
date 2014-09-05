package br.com.procergs.rsmovel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import br.gov.rs.procergs.rsmovel.R;

public class Item_3_Fragment extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	final View v = inflater.inflate(R.layout.item_3_fragment, container, false);
    	
    	TextView tv = (TextView) v.findViewById(R.id.tx5);
    	
    	tv.setText(Html.fromHtml("<a href='http://m.rs.gov.br'>http://m.rs.gov.br</a>"));
    	tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(getActivity(), ContentActivity.class);
				 intent.putExtra("section", "RS Móvel na Web");
				 intent.putExtra("url", "http://m.rs.gov.br");
				 intent.putExtra("item", "RS Móvel na Web");
				 startActivity(intent);
			}
		});
    	
    	
    	final TextView tvTel = (TextView) v.findViewById(R.id.tx3);
    	tvTel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW,	Uri.parse("tel://"+tvTel.getText()));
				 startActivity(intent);
			}
		});
    	
    return v;
        
    }
    
}
