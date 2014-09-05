package br.com.procergs.rsmovel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import br.gov.rs.procergs.prandroidlib.utils.JSONParser;
import br.gov.rs.procergs.prandroidlib.utils.NetworkUtils;
import br.gov.rs.procergs.rsmovel.R;

public class SplashActivity extends Activity {

    //private static final String URL_MENU_JSON = "http://mobile-malvre.rhcloud.com/rsmovel/menu.json";
	private static final String URL_MENU_JSON = "http://m.rs.gov.br/menu.json";
	private static final String TAG_LAST_UPDATE = "last_update";
	private static final String TAG_MENU = "menu";
	JSONArray jsonArray = null;

    @Override
    public void onCreate(Bundle icicle) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(icicle);
        setContentView(R.layout.splash_activity);
        
        new LoadMenuTask().execute(URL_MENU_JSON);
    }
    
	class LoadMenuTask extends AsyncTask<String, String, JSONArray>{
		ProgressDialog dialog = null;
		
		@Override
		protected JSONArray doInBackground(String... params) {
			
			JSONParser jParser = new JSONParser();
			JSONObject json = null;
			JSONObject jsonLocal = null;
			String localLastUpdate = "";
			String remoteLastUpdate = "";

			// cria diretório onde vai ficar o json
			File jsonDir = new File(getCacheDir(), "json");
			File menuJson = new File(jsonDir.getAbsolutePath(), "menu.json");

			try {
				if (NetworkUtils.isNetworkAvailable(getBaseContext())) {

					json = jParser.getJSONFromUrl(params[0]);
					remoteLastUpdate = json.getString(TAG_LAST_UPDATE);

					if (menuJson.isDirectory()) {
						menuJson.delete();
					}

					if (!jsonDir.exists()) {
						jsonDir.mkdir();
					}

					if (!menuJson.exists()) {
						menuJson.createNewFile();
					} else {
						publishProgress("Carregando..");
						
						// se o arquivo já existe, lê o nó "last_update"
						jsonLocal = getJSONFromFile(menuJson);
						localLastUpdate = jsonLocal.getString(TAG_LAST_UPDATE);
						//Log.d("ITEM", "localLastUpdate no splash: " + localLastUpdate);
					}

					// compara as datas de last_update local x remoto
					if (!remoteLastUpdate.equals(localLastUpdate)) {
						publishProgress("Aguarde...");
						
						// se forem diferentes, faz o download da nova versão
						String result = download(params[0]);

						// se o arquivo local existir, escreve dentro dele o
						// conteúdo atualizado
						if (menuJson.exists()) {
							FileOutputStream fos = new FileOutputStream(menuJson);
							fos.write(result.toString().getBytes());
							fos.close();
						}

						//Log.d("ITEM", "feito download do json no splash");
					}
				}

				// recupera o json do arquivo que foi feito o download
				if (jsonLocal != null){
					jsonArray = jsonLocal.getJSONArray(TAG_MENU);
				}else{
					jsonArray = getJSONFromFile(menuJson).getJSONArray(TAG_MENU);
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return jsonArray;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			dialog = new ProgressDialog(SplashActivity.this);
			dialog.setMessage("Aguarde...");
			dialog.show();
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			dialog.setMessage(values[0]);

		}
		
		@Override
		protected void onPostExecute(JSONArray result) {
			super.onPostExecute(result);
			
			if (dialog.isShowing())
				dialog.dismiss();
			
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
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
			//Log.d("ITEM", "arquivo json lido localmente no splash");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jObject;
	}
	
	private String download(String webURL) throws IOException, MalformedURLException {
		StringBuilder response = new StringBuilder();
		BufferedReader input = null;
		String line = null;
		
		URL u = new URL(webURL);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();

		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			input = new BufferedReader(new InputStreamReader(conn.getInputStream()), 8192);

			while ((line = input.readLine()) != null) {
				response.append(line);
			}
			
			input.close();
			//Log.d("ITEM", "arquivo json lido da web no splash");
		}

		return response.toString();
	}
}