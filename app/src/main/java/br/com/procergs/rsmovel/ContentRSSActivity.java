package br.com.procergs.rsmovel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import br.gov.rs.procergs.rsmovel.R;
public class ContentRSSActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss_content);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		String titulo = extras.getString("titulo");
		String data = extras.getString("data");
		String conteudo = extras.getString("conteudo");
		conteudo = conteudo.replaceAll("Leia Mais", "");
		conteudo = conteudo.replaceAll("leia mais", "");
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(Html.fromHtml(titulo));

		TextView date = (TextView) findViewById(R.id.date);
		date.setText(Html.fromHtml(data));

		TextView post = (TextView) findViewById(R.id.post);
		post.setText(Html.fromHtml(conteudo));
		post.setMovementMethod(LinkMovementMethod.getInstance());

	}

}
