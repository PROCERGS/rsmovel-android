package br.com.procergs.rsmovel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.procergs.model.Destaque;
import br.gov.rs.procergs.rsmovel.R;

public class ViewPager_Fragment extends Fragment {

	private static final int MAX_SIZE = 20;
	TextView tvDestaque = null;
	View v = null;

	List<Destaque> content = new ArrayList<Destaque>();
	String[] color = { "#BAC927", "#C93A27", "#D9A133" };

	int index = 0;
	int randomNum = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.v = getView();
		randomNum = new Random().nextInt(color.length);
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.viewpager_fragment, container, false);

		index = getArguments().getInt("section_number");
		content = (List<Destaque>) getArguments().getSerializable("section_content");

		randomNum = new Random().nextInt(color.length);
		v.setBackgroundColor(Color.parseColor(color[randomNum]));

		if (content.size() > 0) {
			tvDestaque = (TextView) v.findViewById(R.id.tvDestaque);

			String titulo = content.get(index - 1).getTitulo();
			tvDestaque.setText(":: " + titulo + " ::");

			// adaptação do tamanho da fonte caso excede o limite de caracteres
			if (titulo.length() > MAX_SIZE) {
				tvDestaque.setPadding(4, 2, 4, 4);
				tvDestaque.setTextSize(18);
			}
			tvDestaque.setTypeface(Typeface.createFromAsset(v.getContext().getAssets(), "Roboto-Light.ttf"),
					Typeface.BOLD);
			tvDestaque.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = null;
					intent = new Intent(getActivity(), ContentActivity.class);

					intent.putExtra("item", content.get(index - 1).getTitulo());
					intent.putExtra("url", content.get(index - 1).getUrl());
					startActivity(intent);
				}
			});
		}

		return v;

	}

	@Override
	public void onResume() {
		super.onResume();
		int randomNum = new Random().nextInt(color.length);
		// Log.d("ITEM", "randomNum no on resume: " + randomNum);
		getView().setBackgroundColor(Color.parseColor(color[randomNum]));
	}

}
