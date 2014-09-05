package br.com.procergs.adapters.rsmovel;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.procergs.model.Child;
import br.gov.rs.procergs.rsmovel.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<Child>> _listDataChild;

	int[] icones = { R.drawable.ico_especial, 	// 0: especial
			R.drawable.ico_servidor,			// 1: servidor público
			R.drawable.ico_transito,			// 2: trânsito
			R.drawable.ico_agricultura,			// 3: agricultura
			R.drawable.ico_seguranca,			// 4: segurança
			R.drawable.ico_agenda,	 			// 5: agenda do governador
			R.drawable.ico_legislativo,			// 6: legislativo
			R.drawable.ico_saude, 				// 7: saúde
			R.drawable.ico_agua, 				// 8: água
			R.drawable.ico_educacao,			// 9: educação
			R.drawable.ico_empresarial,			// 10: empresarial
			R.drawable.ico_administracao,		// 11: administração
			R.drawable.ico_outros, 				// 12: outros
			R.drawable.ico_cidadania, 			// 13: cidadania
			R.drawable.ico_eventos 				// 14: cidadania
	};

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<Child>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
			View convertView, ViewGroup parent) {

		final String childText = ((Child) getChild(groupPosition, childPosition)).getTitulo();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
		}

		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		txtListChild.setText(childText);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		
		String headerTitle = (String) getGroup(groupPosition);

		if (convertView == null) {
			LayoutInflater inflaInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflaInflater.inflate(R.layout.list_group, null);
		}

		ImageView imageview = (ImageView) convertView.findViewById(R.id.iv_icone);
		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(Typeface.createFromAsset(_context.getAssets(), "Roboto-Light.ttf"), Typeface.BOLD);

		if (headerTitle.equalsIgnoreCase("especial")) {
			imageview.setImageResource(icones[0]);
			headerTitle = "Especial";
		}
		if (headerTitle.equalsIgnoreCase("servidor_publico")) {
			imageview.setImageResource(icones[1]);
			headerTitle = "Servidor Público";
		}
		if (headerTitle.equalsIgnoreCase("transito")) {
			imageview.setImageResource(icones[2]);
			headerTitle = "Trânsito";
		}
		if (headerTitle.equalsIgnoreCase("agricultura")) {
			imageview.setImageResource(icones[3]);
			headerTitle = "Agricultura";
		}
		if (headerTitle.equalsIgnoreCase("seguranca")) {
			imageview.setImageResource(icones[4]);
			headerTitle = "Segurança";
		}
		if (headerTitle.equalsIgnoreCase("agenda_do_governador")
				|| headerTitle.equalsIgnoreCase("agenda_governador")) {
			imageview.setImageResource(icones[5]);
			headerTitle = "Agenda do Governador";
		}
		if (headerTitle.equalsIgnoreCase("legislativo")) {
			imageview.setImageResource(icones[6]);
			headerTitle = "Legislativo";
		}
		if (headerTitle.equalsIgnoreCase("saude")) {
			imageview.setImageResource(icones[7]);
			headerTitle = "Saúde";
		}
		if (headerTitle.equalsIgnoreCase("agua_e_luz")) {
			imageview.setImageResource(icones[8]);
			headerTitle = "Água e Luz";
		}
		if (headerTitle.equalsIgnoreCase("educacao")) {
			imageview.setImageResource(icones[9]);
			headerTitle = "Educação";
		}
		if (headerTitle.equalsIgnoreCase("empresarial")) {
			imageview.setImageResource(icones[10]);
			headerTitle = "Empresarial";
		}
		if (headerTitle.equalsIgnoreCase("administracao")) {
			imageview.setImageResource(icones[11]);
			headerTitle = "Administração";
		}
		if (headerTitle.equalsIgnoreCase("outros_servicos")) {
			imageview.setImageResource(icones[12]);
			headerTitle = "Outros";
		}
		if (headerTitle.equalsIgnoreCase("cidadania")) {
			imageview.setImageResource(icones[13]);
			headerTitle = "Cidadania";
		}
		if (headerTitle.equalsIgnoreCase("eventos")) {
			imageview.setImageResource(icones[14]);
			headerTitle = "Eventos";
		}

		//lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
