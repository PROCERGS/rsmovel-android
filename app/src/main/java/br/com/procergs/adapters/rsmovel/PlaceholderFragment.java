package br.com.procergs.adapters.rsmovel;

import java.io.Serializable;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.procergs.model.Destaque;
import br.com.procergs.rsmovel.ViewPager_Fragment;
import br.gov.rs.procergs.rsmovel.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ARG_SECTION_CONTENT = "section_content";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static Fragment newInstance(int sectionNumber, List<Destaque> destaques) {
		Fragment fragment = null;
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putSerializable(ARG_SECTION_CONTENT, (Serializable) destaques);

		fragment = new ViewPager_Fragment();
		fragment.setArguments(args);

		return fragment;
	}

	public PlaceholderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.item_1_fragment, container, false);
		return rootView;
	}
}