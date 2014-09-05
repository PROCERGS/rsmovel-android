package br.com.procergs.adapters.rsmovel;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.com.procergs.model.Destaque;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private List<Destaque> destaques;

	public SectionsPagerAdapter(FragmentManager fm, List<Destaque> destaques) {
		super(fm);
		this.destaques = destaques;
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a PlaceholderFragment (defined as a static inner class
		// below).

		return PlaceholderFragment.newInstance(position + 1, destaques);

	}

	@Override
	public int getCount() {
		return destaques.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return null;
	}
}
