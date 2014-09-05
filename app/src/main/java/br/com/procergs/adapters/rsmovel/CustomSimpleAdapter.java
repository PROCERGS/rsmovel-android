package br.com.procergs.adapters.rsmovel;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import br.gov.rs.procergs.rsmovel.R;

public class CustomSimpleAdapter extends SimpleAdapter {

	public CustomSimpleAdapter(Context context,	List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		if (position % 2 == 1) {
			view.setBackgroundResource(R.drawable.transparent);
		} else {
			view.setBackgroundResource(R.drawable.altercolor2);
		}

		return view;
	}

}
