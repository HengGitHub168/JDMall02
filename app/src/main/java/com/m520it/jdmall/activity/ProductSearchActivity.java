package com.m520it.jdmall.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.m520it.jdmall.R;
import com.m520it.jdmall.adapter.SearchAdapter;
import com.m520it.jdmall.bean.RSearchProduct;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.cons.IntentValues;
import com.m520it.jdmall.contrller.SearchController;
import com.m520it.jdmall.listener.IModelChangeListener;

public class ProductSearchActivity extends BaseActivity implements IModelChangeListener, OnItemClickListener {
	
	private EditText mSearchEt;
	private ListView mSearchLv;
	private View mNullView;
	private SearchAdapter mAdapter;
	private SearchController mController;
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_SEARCH_PRODUCT_RESULT:
				handleSearchProducts((List<RSearchProduct>)msg.obj);
				break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_view);
		
		mController=new SearchController(this);
		mController.setIModelChangeListener(this);
		
		mSearchEt=(EditText) findViewById(R.id.search_et);
		mSearchLv=(ListView) findViewById(R.id.search_lv);
		mNullView=findViewById(R.id.null_view);
		mAdapter=new SearchAdapter(this);
		mSearchLv.setAdapter(mAdapter);
		mSearchLv.setOnItemClickListener(this);
	}
	
	protected void handleSearchProducts(List<RSearchProduct> datas) {
		if (datas.size()==0) {
			mNullView.setVisibility(View.VISIBLE);
			mSearchLv.setVisibility(View.GONE);
		}else {
			mAdapter.setDatas(datas);
			mAdapter.notifyDataSetChanged();
			mNullView.setVisibility(View.GONE);
			mSearchLv.setVisibility(View.VISIBLE);
		}
		if (mLodingDailog.isShowing()) {
			mLodingDailog.dismiss();
		}
	}

	public void searchProduct(View view){
		String searchWord=mSearchEt.getText().toString();
		if (TextUtils.isEmpty(searchWord)) {
			Toast.makeText(this, "请输入搜索的关键词", 800).show();
			return;
		}
		mLodingDailog.show();
		mController.sendAsyncMessage(IDiyMessage.ACTION_SEARCH_PRODUCT,searchWord);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this,ProductDetailsActivity.class);
		RSearchProduct bean = (RSearchProduct) mAdapter.getItem(position);
		intent.putExtra(IntentValues.TO_PRODUCT_DETAILS_KEY, bean.getId());
		startActivity(intent);
	}
	
}
