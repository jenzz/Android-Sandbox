package de.jensdriller.dvd4uCatalogue;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.jensdriller.HelperClasses.GetJSONListener;
import de.jensdriller.HelperClasses.JSONClient;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class DVD4UcatalogueActivity extends ListActivity {

	private Spinner spinner;
	private Context context = this;
	private Builder builder;
	private AlertDialog alertDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		spinner = (Spinner) findViewById(R.id.categories);
		((Button) findViewById(R.id.buttonSearchAgain)).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				showSearchDialog();		
			}
		});
		
		new JSONClient(context, new JSONCategoriesListener()).execute("http://ltw.staff.eda.kent.ac.uk/ecomm/services/getCategories.php");

	}

	private class JSONCategoriesListener implements GetJSONListener {

		public void onJSONDownloadComplete(JSONObject jsonObject) {
			
			try {

				JSONArray jsonCategories = jsonObject.getJSONArray("Categories");
				int numberCategories = jsonCategories.length();
				
				Category[] spinnerCategories = new Category[numberCategories];
				for(int i = 0; i < numberCategories; i++) {
					JSONObject currentCategory = jsonCategories.getJSONObject(i);
					spinnerCategories[i] = new Category(currentCategory.getInt("CategoryId"), currentCategory.getString("CategoryName"));
				}
				
				ArrayAdapter<Category> adapterCategories = new ArrayAdapter<Category>(context, android.R.layout.simple_spinner_item, spinnerCategories);
				adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				spinner.setAdapter(adapterCategories);
				spinner.setOnItemSelectedListener(new SpinnerItemSelectedListener());
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}

		public void onJSONDownloadError(Exception e) {
			
			new AlertDialog.Builder(context)
		      .setTitle("Error")
		      .setMessage(e.toString())
		      .setPositiveButton("OK", null)
		      .show();		
			
		}	
		
	}
	
	private class SpinnerItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			
			Category selectedCategory = (Category) parent.getItemAtPosition(position);
			showProducts(selectedCategory);
			
		}

		public void onNothingSelected(AdapterView<?> parent) {}	
		
	}

	private void showProducts(Category category) {
		
		new JSONClient(context, new JSONProductsListener()).execute("http://ltw.staff.eda.kent.ac.uk/ecomm/services/getByCategory.php?id=" + category.getId());
		
	}
	
	private class JSONProductsListener implements GetJSONListener {

		public void onJSONDownloadComplete(JSONObject jsonObject) {
			
			try {
				
				ArrayList<Product> listProducts = parseProducts(jsonObject);				
		    	ProductAdapter productAdapter = new ProductAdapter(context, R.layout.category_list_item, listProducts);
				setListAdapter(productAdapter);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}

		public void onJSONDownloadError(Exception e) {
			
			new AlertDialog.Builder(context)
		      .setTitle("Error")
		      .setMessage(e.toString())
		      .setPositiveButton("OK", null)
		      .show();
			
		}
		
	}
	
	private ArrayList<Product> parseProducts(JSONObject jsonObject) throws JSONException {
		
		JSONArray jsonProducts = jsonObject.getJSONArray("Products");
		int numberProducts = jsonProducts.length();
		
		ArrayList<Product> listProducts = new ArrayList<Product>();
		for(int i = 0; i < numberProducts; i++) {
			JSONObject currentProduct = jsonProducts.getJSONObject(i);
			listProducts.add(new Product(currentProduct.getInt("ProductId"), currentProduct.getString("ProductName"),
										  currentProduct.getDouble("Price"), currentProduct.getInt("CategoryId"),
										  currentProduct.getString("Description"), currentProduct.getString("Director"),
										  currentProduct.getString("Actors"), currentProduct.getString("Certificate"),
										  currentProduct.getString("Screen"), currentProduct.getInt("Year")));
		}
		
		return listProducts;
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
		
		Product selectedProduct = (Product) l.getItemAtPosition(position);
		
		Intent intent = new Intent(context, ProductDetails.class);
		intent.putExtra("PRODUCT", selectedProduct);
		
		startActivity(intent);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
		
			case R.id.optionsItemSearch:
				showSearchDialog();
				break;
			
			case R.id.optionsItemExit:			
				finish();
				break;

		}
		
		return super.onOptionsItemSelected(item);
		
	}
	
	private void showSearchDialog() {
		
		if(builder == null && alertDialog == null) {
			
			builder = new Builder(this).setTitle(R.string.searchDialogTitle)
			.setIcon(android.R.drawable.ic_menu_search)
			.setPositiveButton(android.R.string.search_go, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {					
					String userSearch = ((EditText) alertDialog.findViewById(R.id.editTextSearch)).getText().toString();
					searchCatalogue(userSearch);
				}
			})
			.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					((EditText) findViewById(R.id.editTextSearch)).setText("");
				}
			})
			.setView(getLayoutInflater().inflate(R.layout.search_dialog, null));
			
			alertDialog = builder.create();
			alertDialog.setOnShowListener(new OnShowListener() {
				
				public void onShow(DialogInterface dialog) {
					if(((EditText) alertDialog.findViewById(R.id.editTextSearch)).getText().length() == 0) {
						alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);	
					}
				}
			});
			
		}	
		
		alertDialog.show();
		EditText editTextSearch = (EditText) alertDialog.findViewById(R.id.editTextSearch);
		
		editTextSearch.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				if(s.length() == 0) {
					alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				} else {
					alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
				}
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}				
			public void afterTextChanged(Editable s) {}
		});
		// force keyboard to popup when dialog opens
		editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			}
		});
		
	}
	
	private void searchCatalogue(String userSearch) {
		
		new JSONClient(context, new JSONSearchListener()).execute("http://ltw.staff.eda.kent.ac.uk/ecomm/services/getProducts.php?str=" + URLEncoder.encode(userSearch));
		
	}
	
	private class JSONSearchListener implements GetJSONListener {

		public void onJSONDownloadComplete(JSONObject jsonObject) {
			
			try {
				
				ArrayList<Product> listProducts = parseProducts(jsonObject);
		    	ProductAdapter productAdapter = new ProductAdapter(context, R.layout.category_list_item, listProducts);
				setListAdapter(productAdapter);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}

		public void onJSONDownloadError(Exception e) {
			
			new AlertDialog.Builder(context)
		      .setTitle("Error")
		      .setMessage(e.toString())
		      .setPositiveButton("OK", null)
		      .show();	
			
		}
		
	}

}