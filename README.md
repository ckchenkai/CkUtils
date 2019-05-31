# CkUtils
## utils组件

#### 使用封装的adapter:
```java
    adapter = new MultiHeaderFooterAdapter<String>(this,list,this) {
            @Override
            protected void onBindData(BaseViewHolder holder, String data, int position)
			{
                holder.setText(R.id.tv_item_data,data+"");
            }
        };
```

  
  compile 'com.ck.project:CkUtils:1.0.0'
