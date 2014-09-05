package br.com.procergs.adapters.rsmovel;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.procergs.rss.Message;
import br.gov.rs.procergs.rsmovel.R;


public class MyArrayAdapter extends ArrayAdapter<Message> {

	private Context context;
	private List<Message> messages;

	public MyArrayAdapter(Context context, List<Message> messages) {
		super(context, 0, messages);
		this.context = context;
		this.messages = messages;
	}

	class Holder {
		TextView title;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		Holder holder = null;

		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.rss_row, parent, false);

			holder = new Holder();
			holder.title = (TextView) row.findViewById(R.id.title);
			row.setTag(holder);
		} else {
			holder = (Holder) row.getTag();
		}
		
		Message m = messages.get(position);
		holder.title.setText(String.valueOf(m.getTitle()));
		return row;

	}

}
