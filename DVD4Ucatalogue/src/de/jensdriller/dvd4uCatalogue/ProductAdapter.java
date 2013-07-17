package de.jensdriller.dvd4uCatalogue;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<Product> {

	private ArrayList<Product> items;

	public ProductAdapter(Context context, int textViewResourceId,
			ArrayList<Product> items) {

		super(context, textViewResourceId, items);
		this.items = items;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.category_list_item, null);
		}

		Product product = items.get(position);
		TextView textViewTitle = (TextView) convertView.findViewById(R.id.item_title);
		TextView textViewSubtitle = (TextView) convertView.findViewById(R.id.item_subtitle);
		if (product != null) {

			textViewTitle.setText(product.getName());
			textViewSubtitle.setText(product.getDirector());

		}
		
		return convertView;

	}
}
