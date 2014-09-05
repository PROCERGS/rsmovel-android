package br.com.procergs.rsmovel;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.procergs.adapters.rsmovel.ExpandableListAdapter;
import br.com.procergs.adapters.rsmovel.SectionsPagerAdapter;
import br.com.procergs.model.Child;
import br.com.procergs.model.Destaque;
import br.com.procergs.rss.Message;
import br.gov.rs.procergs.prandroidlib.adapters.EntryAdapter;
import br.gov.rs.procergs.rsmovel.R;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class Item_1_Fragment extends Fragment {

	private static final String URL_MENU_JSON = "http://m.rs.gov.br/menu.json";
	private static final String TAG_MENU = "menu";
	private static final String TAG_CATEGORIA = "categoria";
	private static final String TAG_ITENS = "itens";
	private static final String TAG_TITULO = "titulo";
	private static final String TAG_URL = "url";

	private static final String TAG_DESTAQUES = "destaques";

	List<Message> listaRetorno = new ArrayList<Message>();
	RelativeLayout topLevelLayout = null;
	ExpandableListAdapter listAdapter = null;
	ExpandableListView expListView = null;
	EntryAdapter listaItems = null;
	JSONArray jsonArray = null;
	JSONArray jsonDestaques = null;
	TextView tv = null;
	ListView lv = null;
	Animation anim = null;
	ViewPager vpDestaques = null;
	List<Destaque> content = null;

	SectionsPagerAdapter mAdapter = null;

	private int currentItem = 0;
	private boolean pagerMoved = false;
	private static final long ANIM_VIEWPAGER_DELAY = 500;

	private Handler h = new Handler();
	private Runnable animateViewPager = new Runnable() {
		public void run() {
			vpDestaques.setCurrentItem(currentItem, true);
			h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.item_1_fragment, container, false);

		content = new ArrayList<Destaque>();
		
		// popula a lista
		prepareListData();

		vpDestaques = (ViewPager) v.findViewById(R.id.vpDestaques);
		anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_in);
		vpDestaques.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				pagerMoved = true;
				h.removeCallbacks(animateViewPager);
				if (pagerMoved == true) {
					h.postDelayed(animateViewPager, 5000);
					currentItem = vpDestaques.getCurrentItem() + 1;
				}
				return false;
			}
		});

		Display display = ((WindowManager) getActivity().getSystemService(Activity.WINDOW_SERVICE))
				.getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int width = display.getWidth(); // Pra manter a compatibilidade com
										// android 2.3

		expListView = (ExpandableListView) v.findViewById(R.id.lvExp);

		// seta a posição do indicator conforme a versão do android
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
			expListView.setIndicatorBounds(0, width + (width / 100 * 92));
		} else {
			expListView.setIndicatorBoundsRelative(width - (width / 100 * 20), 0);
		}

		topLevelLayout = (RelativeLayout) v.findViewById(R.id.top_layout);
		if (isFirstTime()) {
			topLevelLayout.setVisibility(View.INVISIBLE);
		}

		return v;

	}

	@Override
	public void onPause() {
		super.onPause();
		if (h != null) {
			h.removeCallbacks(animateViewPager);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		h.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
		setDestaques();
	}

	private void alternateViewPager() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

		scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				animateViewPager.run();
				currentItem = currentItem + 1;
				if (currentItem >= content.size()) {
					currentItem = 0;
				}
			}
		}, 0, 5, TimeUnit.SECONDS);

	}

	private void redirect(String url, String item) {
		Intent intent = null;
		intent = new Intent(getActivity(), ContentActivity.class);

		intent.putExtra("item", item);
		intent.putExtra("url", url);
		startActivity(intent);
	}

	private void prepareListData() {
		new LoadMenuTask().execute(URL_MENU_JSON);
	}

	class LoadMenuTask extends AsyncTask<String, String, JSONArray> {
		ProgressDialog dialog;

		@Override
		protected JSONArray doInBackground(String... params) {

			JSONObject jsonLocal = null;

			// cria diretório onde vai ficar o json
			File jsonDir = new File(getActivity().getCacheDir(), "json");
			File menuJson = new File(jsonDir.getAbsolutePath(), "menu.json");

			try {
				// se o arquivo já existe, lê o nó "last_update"
				if (menuJson.exists()) {
					jsonLocal = getJSONFromFile(menuJson);
				}

				// recupera o json do arquivo que foi feito o download
				if (jsonLocal != null) {
					jsonArray = jsonLocal.getJSONArray(TAG_MENU);
					jsonDestaques = jsonLocal.getJSONArray(TAG_DESTAQUES);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return jsonArray;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Carregando ...");
			dialog.show();
		}

		@Override
		protected void onPostExecute(JSONArray json) {

			final List<String> listDataHeader = new ArrayList<String>();
			final HashMap<String, List<Child>> listDataChild = new HashMap<String, List<Child>>();

			JSONArray itens = null;

			try {
				setDestaques();
				alternateViewPager();

				List<Child> temp = new ArrayList<Child>();
				Child child = null;

				for (int i = 0; i < json.length(); i++) {
					String categoria = (json.getJSONObject(i).getString(TAG_CATEGORIA));
					itens = json.getJSONObject(i).getJSONArray(TAG_ITENS);

					listDataHeader.add(categoria);

					temp = new ArrayList<Child>();
					for (int j = 0; j < itens.length(); j++) {
						child = new Child();
						child.setTitulo(itens.getJSONObject(j).getString(TAG_TITULO));
						child.setUrl(itens.getJSONObject(j).getString(TAG_URL));
						temp.add(child);
						listDataChild.put(listDataHeader.get(i), temp);
					}
				}

				listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
				expListView.setAdapter(listAdapter);

				expListView.setOnChildClickListener(new OnChildClickListener() {
					@Override
					public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
							int childPosition, long id) {
						String item = ((Child) listAdapter.getChild(groupPosition, childPosition))
								.getTitulo();
						String url = ((Child) listAdapter.getChild(groupPosition, childPosition)).getUrl();
						redirect(url, item);
						return false;
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (dialog.isShowing())
				dialog.dismiss();

		}
	}

	private JSONObject getJSONFromFile(File menu_json) {
		JSONObject jObject = null;
		FileChannel fc = null;
		MappedByteBuffer bb = null;
		String jString = null;
		FileInputStream stream = null;

		try {
			stream = new FileInputStream(menu_json.getAbsolutePath());
			try {
				fc = stream.getChannel();
				bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				jString = Charset.defaultCharset().decode(bb).toString();
			} finally {
				stream.close();
			}

			jObject = new JSONObject(jString);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jObject;
	}

	private boolean isFirstTime() {
		SharedPreferences preferences = getActivity().getPreferences(Activity.MODE_PRIVATE);
		boolean ranBefore = preferences.getBoolean("RanBefore", false);
		if (!ranBefore) {

			SharedPreferences.Editor editor = preferences.edit();
			editor.putBoolean("RanBefore", true);
			editor.commit();

			topLevelLayout.setVisibility(View.VISIBLE);
			topLevelLayout.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					topLevelLayout.setVisibility(View.INVISIBLE);
					return false;
				}

			});

		}
		return ranBefore;

	}

	private void setDestaques() {
		content = new ArrayList<Destaque>();
		File jsonDir = new File(getActivity().getCacheDir(), "json");
		File menuJson = new File(jsonDir.getAbsolutePath(), "menu.json");
		JSONObject jsonLocal = getJSONFromFile(menuJson);
		try {
			Destaque destaque = null;
			JSONArray jsonDestaques = jsonLocal.getJSONArray(TAG_DESTAQUES);
			for (int i = 0; i < jsonDestaques.length(); i++) {
				String titulo = (jsonDestaques.getJSONObject(i).getString("titulo"));
				String url = (jsonDestaques.getJSONObject(i).getString("url"));
				destaque = new Destaque();
				destaque.setTitulo(titulo);
				destaque.setUrl(url);
				content.add(destaque);
			}

//			Destaque destaque1 = new Destaque();
//			destaque1.setTitulo("destaque 1");
//			destaque1.setUrl("www");
//			content.add(destaque1);
//
//			Destaque destaque2 = new Destaque();
//			destaque2.setTitulo("destaque 2");
//			destaque2.setUrl("www 2");
//			content.add(destaque2);
//
			
			mAdapter = new SectionsPagerAdapter(getChildFragmentManager(), content);
			vpDestaques.setAdapter(mAdapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
