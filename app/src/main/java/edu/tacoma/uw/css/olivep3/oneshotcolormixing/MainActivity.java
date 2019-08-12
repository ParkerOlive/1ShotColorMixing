package edu.tacoma.uw.css.olivep3.oneshotcolormixing;

import android.content.Context;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    static TextView formulaResult;
    static TextView infoText;
    //static String result = "";
    static final String url = "http://www.1shot.com/One-Shot/Formulas.aspx?searchtext=";
    static String code = "";
    static Map<String, String> dictionary = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dictionary.put("130L" , "Primrose Yellow");
        dictionary.put("132L" , "Lemon Yellow");
        dictionary.put("134L" , "Chrome Yellow");
        dictionary.put("191L" , "Imitation Gold");
        dictionary.put("124L" , "Orange");
        dictionary.put("100L" , "Vermillion");
        dictionary.put("104L" , "Bright Red");
        dictionary.put("102L" , "Fire Red");
        dictionary.put("108L" , "Maroon");
        dictionary.put("106L" , "Kool Crimson");
        dictionary.put("168L" , "Salmon Pink");
        dictionary.put("120L" , "Coral");
        dictionary.put("164L" , "Dark Magenta");
        dictionary.put("163L" , "Magenta");
        dictionary.put("165L" , "Rubine Red");
        dictionary.put("160L" , "Violet");
        dictionary.put("162L" , "Purple");
        dictionary.put("161L" , "Proper Purple");
        dictionary.put("156L" , "Brilliant Blue");
        dictionary.put("158L" , "Dark Blue");
        dictionary.put("155L" , "Reflex Blue");
        dictionary.put("152L" , "Light Blue");
        dictionary.put("153L" , "Process Blue");
        dictionary.put("154L" , "Peacock Blue");
        dictionary.put("157L" , "Kansas City Teal");
        dictionary.put("150L" , "Blue Green");
        dictionary.put("149L" , "Aqua");
        dictionary.put("151L" , "Robin Egg Blue");
        dictionary.put("148L" , "Dark Green");
        dictionary.put("144L" , "Medium Green");
        dictionary.put("142L" , "Emerald Green");
        dictionary.put("143L" , "Process Green");
        dictionary.put("141L" , "Sublime Green");
        dictionary.put("195L" , "Medium Gray");
        dictionary.put("115L" , "Dark Brown");
        dictionary.put("114L" , "Medium Brown");
        dictionary.put("117L" , "Tan");
        dictionary.put("118L" , "Chamois");
        dictionary.put("116L" , "Ivory");
        dictionary.put("103L" , "Polar White");
        dictionary.put("101L" , "Lettering White");
        dictionary.put("199L" , "Lettering Black");
        dictionary.put("109L" , "Metallic Gold");
        dictionary.put("111L" , "Metallic Brass");
        dictionary.put("193L" , "Metallic Silver");
        dictionary.put("110L" , "Metallic Copper");
        dictionary.put("4001" , "Tinting Black");
        dictionary.put("4002" , "Tinting White");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getFormula(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        infoText = findViewById(R.id.info);
        formulaResult = findViewById(R.id.Formula);
        //result = "";
        code = ((TextView) findViewById(R.id.colorCode)).getText().toString();
        if(code.length() != 0) {
            //formulaResult.setText(code.length()+"");
            new RetreiveFeedTask().execute(url + code);
        } else {
            infoText.setText("Please Enter PMS Code");
        }
//        if(result != "") {
//            result = result.substring(result.indexOf("The 1 Shot color formula for Pantone 1788 is:"));
//        }
        //Toast toast2 = Toast.makeText(getApplicationContext(), code + ": " + result, Toast.LENGTH_SHORT);
        //toast2.show();

    }
}

class RetreiveFeedTask extends AsyncTask<String, Void, Void> {

    protected Void doInBackground(String... urls) {
        String result = "";
        MainActivity.infoText.setText("Loading...");
        MainActivity.formulaResult.setText("");
        //MainActivity.result= "";
        //Execurte the network related option here
        try  {

            //Toast toast = Toast.makeText(getApplicationContext(), "B_jsoup", Toast.LENGTH_SHORT);
            //toast.show();
            Document doc = Jsoup.connect(MainActivity.url+MainActivity.code).get();
            Elements newsHeadlines = doc.getElementsByClass("left columns small-12 large-13 large-offset-1");
            //log(doc.title());
            //Elements newsHeadlines = doc.select("left columns small-12 large-13 large-offset-1");
            for (Element headline : newsHeadlines) {
            result = headline.text(); //= newsHeadlines.text();// .attr("class", "left columns small-12 large-13 large-offset-1").text();
            }
            //MainActivity.result = "This is a test to see if this code is reached";
            if(result != "") {
                if(result.contains("The 1 Shot color formula that you requested is not available.")){
                    MainActivity.infoText.setText(MainActivity.code + " is not a PMS Code.");
                } else {
                    result = result.substring(result.indexOf("The 1 Shot color formula for Pantone"));
                    String info = result.substring(0,result.indexOf("is: ")+4);
                    result = result.substring(result.indexOf("is: ")+4);
                    for(Map.Entry<String, String> color: MainActivity.dictionary.entrySet()){
                        result = result.replace(color.getKey(), color.getKey() + " (" + color.getValue() + ")");
                    }
                    result = result.replace(" – ", "\n");
                    result = result.replace(" - ", "\n");
                    result = result.replace("(VERY DIRTY)", "");
                    MainActivity.infoText.setText(info);
                    MainActivity.formulaResult.setText(result);
                }
            }

        } catch (Exception e) {
//            Toast toast = Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_SHORT);
//            toast.show();
            MainActivity.infoText.setText("Error");
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void param) {

        // TODO: do something with the feed
    }
}


