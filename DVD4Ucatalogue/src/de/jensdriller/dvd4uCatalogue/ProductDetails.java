package de.jensdriller.dvd4uCatalogue;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.Currency;

import de.jensdriller.HelperClasses.ImageDownloader;

public class ProductDetails extends Activity {
	
	protected TextView titleText;
	protected TextView directorText;
	protected TextView screenText;
	protected TextView certText;
	protected TextView actorsText;
	protected TextView descText;
	protected TextView priceText;
	protected ImageView picture;
	
	private final ImageDownloader imageDownloader = new ImageDownloader();
	private Product product;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);        
        
        picture = (ImageView) findViewById(R.id.imageView1);
        
        product = getIntent().getParcelableExtra("PRODUCT");
        
        String URL = "http://ltw.staff.eda.kent.ac.uk/ecomm/images/";
        URL += product.getId() + ".jpg";
        imageDownloader.download(URL, picture);
        
        if(product != null && product instanceof Product) {
        	showDetails();
        }
        
	}

	private void showDetails() {
		
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance("GBP");
        numberFormat.setCurrency(currency);
        
		((TextView) findViewById(R.id.title)).setText(product.getName() + " (" + product.getYear() + ")");
        ((TextView) findViewById(R.id.screen)).setText(product.getScreen());
        ((TextView) findViewById(R.id.cert)).setText("Cert: " + product.getCertificate());
        ((TextView) findViewById(R.id.director)).setText(product.getDirector());
        ((TextView) findViewById(R.id.actors)).setText(product.getActors());
        ((TextView) findViewById(R.id.desc)).setText(product.getDescription());
        ((TextView) findViewById(R.id.price)).setText(currency.getSymbol() + product.getPrice());
        
	}
}
