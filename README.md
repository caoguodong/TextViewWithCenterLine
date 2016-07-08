# TextViewWithCenterLine 
### TextViewWithCenterLine是什么
TextViewWithCenterLine 是一个建立在android原生TextView控件之上，加入了中划线的项目。
可以设置中划线是否显示，可以设置中划线的颜色和粗细。
但是不可以设置中划线的长度。
中划线的长度应该一直跟TextView里面的文字一样长，不管是否设置了padding，margin，还是设置了drawable。
### TextViewWithCenterLine怎么用？
TextViewWithCenterLine是一个空间，所以直接在布局里面引用相应布局就好了
控件里面自定义了三个属性，可以调节中划线的颜色，宽度，是否显示
######app:centerLineWidth="2dp"  这个属性设置中划线的宽度
######app:showCenterLine="true"  这个属性设置中划线是否显示
######app:centerLineColor="@color/colorAccent" 这个属性设置中划线的颜色
    
