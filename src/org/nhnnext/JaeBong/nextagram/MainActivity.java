package org.nhnnext.JaeBong.nextagram;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements OnItemClickListener, OnClickListener {

	private ArrayList<Article> articleList = new ArrayList<Article>();
	private Button writeBtn;
	private Button refreshBtn;
	private ListView mainListView1;
	private final Handler handler = new Handler();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Button ����
        writeBtn = (Button)findViewById(R.id.custom_list_write);
        refreshBtn = (Button)findViewById(R.id.custom_list_refresh);
       
        //Button�� OnClickListener ���� 
        writeBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
        
        mainListView1 = (ListView)findViewById(R.id.custom_list_listView);
           
   
        
    }
    
    public boolean onCreateOptionsMenu(Menu menu){
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    public void onResume(){
    	super.onResume();
    	refreshData();
    }
    
    private void refreshData(){
		new Thread(){
			public void run(){
				//�������� Json�޾ƿ� 
		        Proxy proxy = new Proxy();
		        String jsonData = proxy.getJson();
		        
		        //DataBase���� ������ �о�� 
		        Dao dao = new Dao(getApplicationContext());
		        dao.insertJsonData(jsonData);
		        
		        handler.post(new Runnable(){
		        	public void run(){
		        		listView();   
		        	}
		        });
			}
		}.start();	
	}
	
	private void listView(){
        //DataBase���� ������ �о�� 
        Dao dao = new Dao(getApplicationContext());
        articleList = dao.getArticleList();
        
        //Main�� ListView�� Ŀ���� ����� ����.
  
        CustomAdapter_articleList customAdapter = new CustomAdapter_articleList(this, R.layout.activity_custom_list_row, articleList);
        mainListView1.setAdapter(customAdapter);
        mainListView1.setOnItemClickListener(this);
	}
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		Intent intent = new Intent(this,ArticleViewer.class);
		intent.putExtra("ArticleNumber", articleList.get(position).getArticleNumber()+ "");
		startActivity(intent);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.custom_list_write:
			Intent writeView = new Intent(this,ArticleWriter.class);
			startActivity(writeView);
			break;
		case R.id.custom_list_refresh:
			refreshData();
			break;
		}
	}
}
