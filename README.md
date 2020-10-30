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


#### 使用封装的CustomDialog:
```java
 CustomDialog dialog = new CustomDialog.Builder(this)
                    .setCancleable(isCancleable)
                    .setHideCancleBtn(isHideCancleBtn)
                    .setContent("这是内容")
                    .setOnKeyBackListener(isSetKeyback ? onKeyListener() : null)
                    .setWidthRadio(isSetWidthRadio ? 0.5 : 0)
                    .setCommitListener(()->ToastUtils.getInstance().showToast(this,"确定"))
                    .setCancleListener(()->ToastUtils.getInstance().showToast(this,"取消"))
                    .create();
            dialog.show()
```



 ######  Add it in your root build.gradle at the end of repositories:

   	allprojects {
   		repositories {
   			...
   			maven { url 'https://www.jitpack.io' }
   		}
   	}


 ###### Add the dependency
   	dependencies {
   	        implementation 'com.github.ckchenkai:CkUtils:1.0'
   	}
