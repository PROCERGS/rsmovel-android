package br.com.procergs.rsmovel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import br.com.procergs.adapters.rsmovel.MenuAdapter;
import br.com.procergs.navigation.rsmovel.PRNavigationDrawer;
import br.gov.rs.procergs.prandroidlib.adapters.EntryAdapter;
import br.gov.rs.procergs.prandroidlib.utils.Alert;
import br.gov.rs.procergs.rsmovel.R;

public class MainActivity extends FragmentActivity {

	private static final String SEPARADOR = System.getProperty("line.separator");
	protected static final String TAG = MainActivity.class.getSimpleName();

	private String[] namesMenu;
	private String[] classesMenu;
	private DrawerLayout drawerLayout;
	private ListView drawerListView;
	private PRNavigationDrawer drawer;
	private EntryAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new MenuAdapter(this).getStringAdapter();
		namesMenu = getResources().getStringArray(R.array.menu_itens);
		classesMenu = getResources().getStringArray(R.array.menu_classes);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		drawerListView = (ListView) findViewById(R.id.drawer_list_view);
		
		drawer = new PRNavigationDrawer(this, drawerListView, namesMenu, classesMenu, adapter, drawerLayout);
		drawer.openDrawer();
		drawer.drawerItemClickListener();
		
		if (savedInstanceState == null){
			drawer.setContent(0);
		}
		
//		if (isInstalled()){
//			createShortcut();
//		}
		
	}


	/**
	 *  Criação de atalho na home screen, caso
	 *  decida-se que será usado
	 */
	@SuppressWarnings("unused")
	private void createShortcut() {
        String appName = getString(R.string.app_name);

        Intent shortcutIntent = new Intent(getApplicationContext(), SplashActivity.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY );

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        addIntent.putExtra("duplicate", false);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,

        Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);

    }
	
	/**
	 * IMPORTANTE! O método syncState sincroniza o estado do DrawerLayout apos
	 * um onRestoreInstanceState
	 */
	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawer.getDrawerToggle().syncState();
	}
	
	/**
	 * Tratamento do item selecionado no menu da action bar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		/**
		 * Habilita o botão de navegação do Navigation Drawer situado à esquerda
		 * superior da action bar
		 */
		drawer.getDrawerToggle().onOptionsItemSelected(item);

		switch (item.getItemId()) {
		/*
		case R.id.menu_item_share:
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = "Endereço no SVN: svn.procergs.reders/AMP_Base_1.0";
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Repositório APM Base 1.0 no SVN");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent, getResources().getText(R.id.menu_item_share)));
			break;
		*/
		case R.id.menu_agenda:
			Intent intent = new Intent(this, ContentActivity.class);
			intent.putExtra("item", item.getTitle());
			intent.putExtra("url", "http://m.rs.gov.br/site/agenda");
			startActivity(intent);
			break;
		case R.id.menu_sobre:
			showAboutDialog();
			//drawer.setContent(new Item_3_Fragment(), item.toString());
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	
	/**
	 * Exemplo de dialog utilizando o método utilitário Alert para mostrar o
	 * item Sobre
	 */
	public void showAboutDialog() {
		StringBuffer msg = new StringBuffer();
		msg.append("PROCERGS Mobile © 2013 ");
		msg.append(SEPARADOR);
		msg.append(SEPARADOR);
		msg.append("v1.0");
		msg.append(SEPARADOR);
		msg.append(SEPARADOR);
		msg.append("Governo do Estado do RS");

		Alert.show(this, R.string.action_sobre, msg.toString());
	}

	@SuppressWarnings("unused")
	private boolean isInstalled() {
		SharedPreferences preferences = getPreferences(Activity.MODE_PRIVATE);
		boolean isInstalled = preferences.getBoolean("RSMovelInstalled", false);
		if (!isInstalled) {

			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean("RSMovelInstalled", true);
			editor.commit();
			
		}
		return isInstalled;

	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
