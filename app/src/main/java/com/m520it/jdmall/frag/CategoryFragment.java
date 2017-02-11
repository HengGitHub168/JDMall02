package com.m520it.jdmall.frag;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.m520it.jdmall.R;
import com.m520it.jdmall.activity.ProductSearchActivity;
import com.m520it.jdmall.adapter.TopCategoryAdapter;
import com.m520it.jdmall.bean.RBaseCategory;
import com.m520it.jdmall.cons.IDiyMessage;
import com.m520it.jdmall.contrller.CategoryController;
import com.m520it.jdmall.listener.IModelChangeListener;
import com.m520it.jdmall.view.SubCategoryView;

/**
 *	主页-分类页
 */
public class CategoryFragment extends BaseFragment 
							implements IModelChangeListener, OnClickListener {
	
	private ListView mTopCategoryLv;
	private SubCategoryView mSubCategoryView;
	private TopCategoryAdapter mAdapter;
	private EditText mSearchEt;
	private CategoryController mController;
	private Handler mHandler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IDiyMessage.ACTION_GET_TOP_CAREGORY_RESULT:
				handleLoadTopCategory((ArrayList<RBaseCategory>) msg.obj);
				break;
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_category, null, false);
	}
	
	private void handleLoadTopCategory(ArrayList<RBaseCategory> rBaseCategorys) {
		mAdapter.setBeans(rBaseCategorys);
		mAdapter.notifyDataSetChanged();
		if (rBaseCategorys.size()>0) {
			touchTopCategory(0);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mController=new CategoryController(getActivity());
		mController.setIModelChangeListener(this);
		
		mSearchEt=(EditText)getActivity().findViewById(R.id.search_et);
		mSearchEt.setOnClickListener(this);
		mTopCategoryLv=(ListView) getActivity().findViewById(R.id.top_lv);
		mSubCategoryView=(SubCategoryView) getActivity().findViewById(R.id.subcategory);
		mAdapter=new TopCategoryAdapter(getActivity());
		mTopCategoryLv.setAdapter(mAdapter);
		mTopCategoryLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				touchTopCategory(position);
			}
		});
		mController.sendAsyncMessage(IDiyMessage.ACTION_GET_TOP_CAREGORY, 0);
	}

	private void touchTopCategory(int position) {
		mAdapter.mPosition=position;
		mAdapter.notifyDataSetChanged();
		RBaseCategory topCategory = (RBaseCategory) mAdapter.getItem(position);
		mSubCategoryView.show(topCategory);
	}

	@Override
	public void onModelChanged(int action, Object... values) {
		mHandler.obtainMessage(action,values[0]).sendToTarget();
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent(getActivity(),ProductSearchActivity.class);
		startActivity(intent);
	}
	
}
