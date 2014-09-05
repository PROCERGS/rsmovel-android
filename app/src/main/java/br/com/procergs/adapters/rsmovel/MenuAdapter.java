/**
 * Classe respons�vel pela montagem do menu
 * de navega��o no padr�o de Navigation Drawer
 * 
 * Esta classe recebe par�metros do controller para 
 * designar seus itens de menu e coloc�-los dentro de
 * uma estrutura que alimenta o Navigation Drawer
 * 
 * @author evertoncanez
 */

package br.com.procergs.adapters.rsmovel;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import br.gov.rs.procergs.prandroidlib.adapters.EntryAdapter;
import br.gov.rs.procergs.prandroidlib.list.item.EntryItem;
import br.gov.rs.procergs.prandroidlib.list.item.Item;
import br.gov.rs.procergs.prandroidlib.list.item.SectionItem;
import br.gov.rs.procergs.rsmovel.R;

public class MenuAdapter {
	
	private Context context;
	private TypedArray icones;
	private String[] nomes;
	private ArrayList<Item> items;
	
	/**
	 * Construtor para o carregamento
	 * dos itens de menu existentes no
	 * arquivo de resources chamado arrays.xml
	 * @param ctx
	 */
	public MenuAdapter(Context ctx) {
		this.context = ctx;
		this.nomes   = this.context.getResources().getStringArray(R.array.menu_itens);
		this.icones  = this.context.getResources().obtainTypedArray(R.array.menu_icones);
		icones.recycle();
	}
	
	/**
	 * Método para montagem do menu
	 * que será exibido na navegação 
	 * do app
	 * 
	 * @return Um objeto EntryAdapter
	 * 
	 * EntryAdapter é o objeto responsável
	 * pela manipulação da view
	 */
	public EntryAdapter getXmlAdapter(){
		
		this.items	 = new ArrayList<Item>();
		
		items.add(new SectionItem("MENU"));
		items.add(getEntryItem(0));
		items.add(getEntryItem(1));
		items.add(getEntryItem(2));
		items.add(getEntryItem(3));
		items.add(getEntryItem(4));
		
		items.add(new SectionItem("EXEMPLOS"));
		items.add(getEntryItem(5));
		items.add(getEntryItem(6));
		items.add(getEntryItem(7));
		
		EntryAdapter adapter = new EntryAdapter(this.context, items);

		return adapter;
	}
	
	public EntryAdapter getStringAdapter(){
		
		this.items = new ArrayList<Item>();
		
		items.add(new SectionItem("MENU"));
		items.add(getEntryItem(0, 	R.string.menu_item_0, 	R.drawable.ic_servicos_disponiveis));
		items.add(getEntryItem(1, 	R.string.menu_item_1, 	R.drawable.ic_termo_servico));
		items.add(getEntryItem(2, 	R.string.menu_item_2, 	R.drawable.ic_sobre));		
		EntryAdapter adapter = new EntryAdapter(this.context, items);

		return adapter;
		
	}

	public EntryAdapter getMenuTelaPrincipal(){
		
		this.items = new ArrayList<Item>();
		
		items.add(new SectionItem("Notícias"));
		items.add(getEntryItem(0, "Previsão de agendas das secretarias e vinculadas para esta quinta-feira, 03 de setembro"));
		items.add(getEntryItem(1, "Jardim Botânico recebe visita de professores alemães"));
		
		items.add(new SectionItem("Especial"));
		items.add(getEntryItem(2, "Copa 2014"));
		items.add(getEntryItem(3, "Expointer 2013"));
		
		items.add(new SectionItem("Agenda do Governador"));
		items.add(getEntryItem(4, "Compromissos do Dia"));
		
		items.add(new SectionItem("Agricultura"));
		items.add(getEntryItem(5, "Consulta Produtor Rural"));
		items.add(getEntryItem(6, "Previsão do Tempo"));
		
		items.add(new SectionItem("Água e Saneamento"));
		items.add(getEntryItem(6, "CORSAN"));
		
		items.add(new SectionItem("Administração"));
		items.add(getEntryItem(7, "Processos Administrativos"));
		
		items.add(new SectionItem("Saúde"));
		items.add(getEntryItem(8, "Assistência Farmacêutica"));
		items.add(getEntryItem(9, "Guia Médico do IPE"));
		items.add(getEntryItem(10, "Informações Toxicológicas"));
		
		items.add(new SectionItem("Segurança"));
		items.add(getEntryItem(11, "Denuncie"));
		items.add(getEntryItem(12, "Auxilie"));
		items.add(getEntryItem(13, "Delegacias e Postos da Polícia Civil"));
		
		items.add(new SectionItem("Servidor Público"));
		items.add(getEntryItem(14, "Calendário de Pagamentos"));
		items.add(getEntryItem(15, "Contracheque - RHE"));

		items.add(new SectionItem("Trânsito"));
		items.add(getEntryItem(16, "Consulta Veículos"));
		items.add(getEntryItem(17, "Consulta Infrações"));
		items.add(getEntryItem(18, "Consulta CNH"));
		items.add(getEntryItem(19, "Consulta Pontuação"));
		items.add(getEntryItem(20, "Consulta Veículos em Depósito"));
		
		items.add(new SectionItem("Empresarial"));
		items.add(getEntryItem(21, "Andamento de Processos"));
		items.add(getEntryItem(22, "Reserva de Nome Empresarial"));
		items.add(getEntryItem(23, "Como Faço e Obtenho"));
		items.add(getEntryItem(24, "Atos e Eventos"));
		items.add(getEntryItem(25, "Locais de Atendimento"));
		items.add(getEntryItem(26, "Tabelas de Preços"));
		
		items.add(new SectionItem("Legislativo"));
		items.add(getEntryItem(27, "Consultas Legislativas"));
		
		items.add(new SectionItem("Educação"));
		items.add(getEntryItem(28, "Matrícula na Escola Pública"));
		
		items.add(new SectionItem("Outros Serviços"));
		items.add(getEntryItem(29, "Correios"));
		
		EntryAdapter adapter = new EntryAdapter(this.context, items);

		return adapter;
		
	}

	private EntryItem getEntryItem(int index, String item) {
		EntryItem entryItem = new EntryItem(index, item);
		return entryItem;
	}

	/**
	 * Método para montar o menu sem o uso
	 * do arquivo de resources arrays.xml
	 * 
	 * Os itens devem ser acrescentados na 
	 * estrutura de lista diretamente no 
	 * código
	 * 
	 * @param index
	 * @param resItem
	 * @param drawableIconId
	 * @return
	 */
	private EntryItem getEntryItem(int index, int resItem, int drawableIconId) {
		Drawable icone = null;
		if (drawableIconId > 0)
			icone = this.context.getResources().getDrawable(drawableIconId);
		String item  = this.context.getResources().getString(resItem);
		EntryItem entryItem = new EntryItem(index, item, icone);
		return entryItem;
	}

	/**
	 * Método responsável pela montagem
	 * do menu a partir do arquivo de resources
	 * arrays.xml
	 * 
	 * @param index
	 * 
	 * O parâmetro index é o identificador que indica
	 * a posição do item de menu. Dentro da sua estrutura,
	 * é este o paâmetro que irá indicar qual ação
	 * deve ser chamada quando um item for acionado.
	 * 
	 * @return EntryItem
	 */
	private EntryItem getEntryItem(int index) {
		int iconesResId = icones.getResourceId(index, R.drawable.ic_launcher);
		//Log.d("ITEM", String.valueOf(iconesResId));
		EntryItem entryItem = new EntryItem(index, nomes[index], iconesResId);
		return entryItem;
	}

}
