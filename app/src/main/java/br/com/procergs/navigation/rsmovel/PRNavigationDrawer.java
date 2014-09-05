package br.com.procergs.navigation.rsmovel;

/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.gov.rs.procergs.rsmovel.R;
import br.gov.rs.procergs.prandroidlib.list.item.EntryItem;
import br.gov.rs.procergs.prandroidlib.list.item.Item;
import br.gov.rs.procergs.prandroidlib.adapters.EntryAdapter;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PRNavigationDrawer {

	static final String OPENED_KEY = "OPENED_KEY";

	private ActionBarDrawerToggle actionBarDrawerToggle;
	private EntryAdapter adapter;
	private SharedPreferences prefs;
	private Boolean opened;
	private DrawerLayout drawerLayout;
	private ListView drawerListView;
	private String[] drawerNames;
	private String[] drawerClasses;
	private Activity act;
	
	public PRNavigationDrawer() {
	}

	public PRNavigationDrawer(Activity act, ListView listView, String[] names,
			String[] classes, EntryAdapter adapter, DrawerLayout layout) {
		this.act = act;
		this.drawerListView = listView;
		this.drawerListView.setAdapter(adapter);
		this.drawerNames = names;
		this.drawerClasses = classes;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			//this.actionBarDrawerToggle = desenhaDrawer(act.getActionBar().getThemedContext(), layout);
			this.actionBarDrawerToggle = desenhaDrawer(act.getBaseContext(), layout);
		}else{
			this.actionBarDrawerToggle = desenhaDrawer(act.getBaseContext(), layout);
		}
		this.adapter = adapter;
		this.drawerLayout = layout;
		this.drawerLayout.setDrawerListener(actionBarDrawerToggle);

		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR2) {
			act.getActionBar().setDisplayHomeAsUpEnabled(true);
			act.getActionBar().setHomeButtonEnabled(true);
		}
	}

	private ActionBarDrawerToggle desenhaDrawer(Context ctx, DrawerLayout drawerLayout) {
		return new ActionBarDrawerToggle(act, drawerLayout,	R.drawable.ic_drawer, R.string.abrir, R.string.fechar) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR2) {
					act.invalidateOptionsMenu();
				}else{
					ActivityCompat.invalidateOptionsMenu(act); 
				}
				if (opened != null && opened == false) {
					opened = true;
					if (prefs != null) {
						Editor editor = prefs.edit();
						editor.putBoolean(OPENED_KEY, true);
						editor.apply();
					}
				}
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR2) {
					act.getActionBar().setTitle(R.string.app_name);
					act.invalidateOptionsMenu();
				}else{
					ActivityCompat.invalidateOptionsMenu(act);
				}

			}
		};
	}

	/**
	 * Controle do item que foi selecionado no menu drawer
	 */
	public void drawerItemClickListener() {
		drawerListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {
				
				final Item item = adapter.getItems().get(pos);
				if (item != null) {
					EntryItem ei = (EntryItem) item;
					int itemPos = adapter.getItemPos(ei);

					setContent(itemPos);
					drawerLayout.closeDrawer(drawerListView);
				}
			}
		});
	}
 	
	public void setContent(int selection) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR2) {
			act.getActionBar().setTitle(drawerNames[selection]);
		}
		FragmentTransaction tx = ((FragmentActivity) act).getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.frameLayout, Fragment.instantiate(act, drawerClasses[selection]));
		tx.commit();
	}

	public void setContent(Fragment fragment, String item) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR2) {
			act.getActionBar().setTitle(item);
		}
		FragmentTransaction tx = ((FragmentActivity) act).getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.frameLayout, fragment);
		tx.commit();
	}

	public void openDrawer() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR2) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					prefs = act.getPreferences(Activity.MODE_PRIVATE);
					opened = prefs.getBoolean(OPENED_KEY, false);
					if (opened == false) {
						drawerLayout.openDrawer(drawerListView);
					}
				}
			}).start();
		}
	}

	public ActionBarDrawerToggle getDrawerToggle() {
		return actionBarDrawerToggle;
	}
}
