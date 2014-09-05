package br.com.procergs.rsmovel;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.gov.rs.procergs.prandroidlib.adapters.EntryAdapter;
import br.gov.rs.procergs.rsmovel.R;

public class Item_2_Fragment extends Fragment {

	static ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	EntryAdapter lista;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.item_2_fragment, container, false);

		return v;

	}

}
