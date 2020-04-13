/*
 Ajax 三级省市联动
 settings 参数说明
 -----
 data:省市数据的json数据---因为可能是我的全国地区数据有点大，400kb以上，用异步加载会down，所以只能这样解决了。
 selectValue:选中的数据，请注意，选中的数据格式如下：400,500,100 可以null，空，一个数据，两个或三个数据，
 系统会优先将selectValue处理成为prov city country三个参数。假如四个参数同时出现，会处理selectValue，忽略prov city country三个参数。
 separator:分隔符，默认为逗号.
 province:默认省份
 city:默认城市
 country:默认地区（县）
 nodata:无数据状态
 required:必选项,
 province_required:省是否必填,
 city_required:市是否必填,
 country_required:县是否必填,
 callBack:回调函数，每次选中以后进行的处理。注意，选中后回调函数会有两个参数，第一个参数为选中的地区的id，中间用逗号分开，譬如：
 广东省代号 21000，广州市代号为21010，天河区代号为21015，那么传进来的参数就为：21000,21010,21015  当然这是举例子。
 第二个参数为名称，譬如：广东,广州,天河区。
 onfocus:function(){} 当用户点击三个select任意一个都触发。
 使用说明：
  【1、如果页面只有一个省市县，则直接通过这种方式使用】
 	$("#province").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        province_required:true,
        city_required:true,
        country_required:false,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
        		}
        	}
        }
    });
    【2、如果页面有多个省市县，则省市县外面包一个div】
     	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        province_required:true,
        city_required:true,
        country_required:false,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
        		}
        	}
        }
    });

 ------------------------------ */
(function($){
    $.fn.citySelect=function(settings){
        if(this.length<1){return;}
        //是否为空字符串
        var isEmpty = function (OriginString) {
            var currentStr = $.trim(OriginString);
            if (currentStr == "") { return true; }
            else { return false; }
        };
        // 默认值
        settings=$.extend({
            selectValue:null,
            separator:',',
            data:null,
            province:null,
            city:null,
            country:null,
            nodata:null,
            province_required:false,
            city_required:false,
            country_required:false,
            required:false,
            onfocus:function(){
                    // console.log("注意我了。");
            },
            callback:function(selectValue,selectText){
                //--请覆盖这个方法。
               // console.log("【用户选择的地区：】");
               // console.log(selectValue);
              //  console.log(selectText);
            }
        },settings);
        var box_obj=this;
        var _this=this;
        var province_obj=box_obj.find("#province");
        var city_obj=box_obj.find("#city");
        var country_obj=box_obj.find("#country");
        if($(this).attr("id") == "province"){//如果根据provice定义的，则直根据ID，因为触屏版不需要外面包一个div影响样式
        	province_obj=$("#province");
            city_obj=$("#city");
            country_obj=$("#country");
        }
        var prov_val=settings.province;
        var city_val=settings.city;
        var country_val=settings.country;
        var province_required=settings.province_required;
        var city_required=settings.city_required;
        var country_required=settings.country_required;
        if(province_required){
        	province_obj.attr({"datatype": "Require", "msg": "请选择省份"});
        }
        if(city_required){
        	city_obj.attr({"datatype": "Require", "msg": "请选择市"});
        }
        if(country_required){
        	country_obj.attr({"datatype": "Require", "msg": "请选择区/县"});
        }
        //--按照
        if(settings.selectValue!=null){
            var _arr1= settings.selectValue.split(settings.separator);
            var _arr2=new Array();
            if(_arr1!=null&&_arr1.length>0){
                for(var str_1 in _arr1){
                    if(isEmpty(str_1)){
                        continue;
                    }
                    _arr2.push(str_1);
                }
            }
            settings.province=null;
            settings.city=null;
            settings.country=null;
            if(_arr2.length>=1){
                settings.province=_arr2[0];
            }
            if(_arr2.length>=2){
                settings.city=_arr2[1];
            }
            if(_arr2.length>=3){
                settings.country=_arr2[2];
            }
        }
        var select_prehtml=(settings.required) ? "" : "<option value=''>请选择</option>";
        var city_json=settings.data;
        // 赋值市级函数
        var cityStart=function(){
            var prov_id=province_obj.find("option:selected").val();
            if(prov_id==undefined||prov_id==null||prov_id==""){
                //--取第一个option为id
                prov_id=province_obj.find("option:first").val();
            }
            /*
            if(!settings.required){
                prov_id--;
            };
            */
            //--没有选中任何省份。。。
            if(prov_id==undefined||prov_id==null||prov_id==""){
                city_obj.empty().attr("disabled",true);
                country_obj.empty().attr("disabled",true);
                //city_obj.css("display","none");
               //country_obj.css("display","none");
                return;
            }
            //--选中了省份，那么看看有没有该省份资料，且看看省份下面有多少城市。
            if(city_json[prov_id+""]==undefined||caculate_json_size(city_json[prov_id]["child"])<=0){
                city_obj.empty().attr("disabled",true);
                country_obj.empty().attr("disabled",true);
                //city_obj.css("display","none");
                //country_obj.css("display","none");
                return;
            }
            //--有省份的城市列表，那么就可以加上去了。
            // 遍历赋值市级下拉列表
            temp_html=select_prehtml;
            var _childData= city_json[prov_id]["child"];
            for(var _cityKey in _childData){
                var _cityItem=_childData[_cityKey];
                temp_html+="<option value='"+_cityItem["id"]+"'>"+_cityItem["name"]+"</option>";
            }
            city_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
            countryStart();
        };

        // 赋值地区（县）函数
        var countryStart=function(){
            var prov_id=province_obj.find("option:selected").val();

                //province_obj.get(0).selectedIndex;
            var city_id=city_obj.find("option:selected").val();
            var _prov_no_selected=false;
            if(prov_id==undefined||prov_id==null||prov_id==""){
                _prov_no_selected=true;
            }
            var _city_no_selected=false;
            if(city_id==undefined||city_id==null||city_id==""){
                _city_no_selected=true;
            }
            //--城市没有选择的，那么镇区就不要显示出来了。
            if(_city_no_selected){
                country_obj.empty().attr("disabled",true);
                //country_obj.css("display","none");
                return;
            }
            //--选中了城市，那么看看有没有该城市资料，且看看城市下面有多少镇区。
            if(city_json[prov_id+""]==undefined||caculate_json_size(city_json[prov_id]["child"])<=0||city_json[prov_id]["child"][city_id]==undefined||caculate_json_size(city_json[prov_id]["child"][city_id])<0){
                country_obj.empty().attr("disabled",true);
                //country_obj.css("display","none");
                return;
            }
            // 遍历赋值市级下拉列表
            temp_html=select_prehtml;
            var _childData= city_json[prov_id]["child"][city_id]["child"];
            for(var _townKey in _childData){
                var _townItem=_childData[_townKey];
                temp_html+="<option value='"+_townItem["id"]+"'>"+_townItem["name"]+"</option>";
            }
            country_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
        };
        var caculate_json_size=function(jdata){
            if(jdata==undefined||jdata==null||jdata==""){
                return 0;
            }
            var _size=0;
            for(var key in jdata){
               _size++;
            }
            return _size;
        }

        var init=function(){
            // 遍历赋值省份下拉列表
            temp_html=select_prehtml;
            var _arr_html=new Array();
            for(var key in city_json){
                var _item=city_json[key];
                _arr_html.push("<option value='"+_item["id"]+"'>"+_item["name"]+"</option>");
            }
            temp_html+=_arr_html.join(" ");
            province_obj.html(temp_html);

            // 若有传入省份与市级的值，则选中。（setTimeout为兼容IE6而设置）
            setTimeout(function(){
                if(settings.province!=null){
                    province_obj.val(settings.province);
                    cityStart();
                    setTimeout(function(){
                        if(settings.city!=null){
                            city_obj.val(settings.city);
                            countryStart();
                            setTimeout(function(){
                                if(settings.country!=null){
                                    country_obj.val(settings.country);
                                };
                                _callBackHandler();
                            },1);
                            
                        };
                        _callBackHandler();
                    },1);
                }else{
                    province_obj.val(province_obj.find("option:first").val());
                    cityStart();
                    setTimeout(function(){
                            city_obj.val(city_obj.find("option:first").val());
                            countryStart();
                            setTimeout(function(){
                                city_obj.val(country_obj.find("option:first").val());
                                _callBackHandler();
                            },1);
                            _callBackHandler();
                    },1);
                }
                //--最后执行callback
                _callBackHandler();
            },1);

            // 选择省份时发生事件
            province_obj.bind("change",function(){
                cityStart();
                _callBackHandler();
            });

            // 选择市级时发生事件
            city_obj.bind("change",function(){
                countryStart();
                _callBackHandler();
            });
            country_obj.bind("change",function(){
                _callBackHandler();
            });
            province_obj.focus(function(){
                settings.onfocus();
            });
            city_obj.focus(function(){
                settings.onfocus();
            });
            country_obj.focus(function(){
                settings.onfocus();
            });
        };
        //--初始化后整个控件。
       init();
var _callBackHandler=function(){
    var _prov_id=province_obj.find("option:selected").val();
    var _city_id=city_obj.find("option:selected").val();
    var _country_id=country_obj.find("option:selected").val();
    var _prov_name=province_obj.find("option:selected").text();
    var _city_name=city_obj.find("option:selected").text();
    var _country_name=country_obj.find("option:selected").text();
    if(_prov_id==undefined||_prov_id==null||_prov_id==""){
        settings.callback("","");
        return;
    }
    //--有省份id，那么判断市的id。
    if(_city_id==undefined||_city_id==null||_city_id==""){
        settings.callback(_prov_id+"",_prov_name);
        return;
    }
    //--有市id，那么判断乡镇id。
    if(_country_id==undefined||_country_id==null||_country_id==""){
        settings.callback(_prov_id+settings.separator+_city_id,_prov_name+settings.separator+_city_name);
        return;
    }
    settings.callback(_prov_id+settings.separator+_city_id+settings.separator+_country_id,_prov_name+settings.separator+_city_name+settings.separator+_country_name);
}
        this.getValue=function(){
            var _prov_id=province_obj.find("option:selected").val();
            var _city_id=city_obj.find("option:selected").val();
            var _country_id=country_obj.find("option:selected").val();
            var _prov_name=province_obj.find("option:selected").text();
            var _city_name=city_obj.find("option:selected").text();
            var _country_name=country_obj.find("option:selected").text();
            if(_prov_id==undefined||_prov_id==null||_prov_id==""){
                return "";
            }
            //--有省份id，那么判断市的id。
            if(_city_id==undefined||_city_id==null||_city_id==""){
                return _prov_id;
            }
            //--有市id，那么判断乡镇id。
            if(_country_id==undefined||_country_id==null||_country_id==""){
                return _prov_id+settings.separator+_city_id;
                return;
            }
            return _prov_id+settings.separator+_city_id+settings.separator+_country_id;
        }
        this.getText=function(){
            var _prov_id=province_obj.find("option:selected").val();
            var _city_id=city_obj.find("option:selected").val();
            var _country_id=country_obj.find("option:selected").val();
            var _prov_name=province_obj.find("option:selected").text();
            var _city_name=city_obj.find("option:selected").text();
            var _country_name=country_obj.find("option:selected").text();
            if(_prov_id==undefined||_prov_id==null||_prov_id==""){
                return "";
            }
            //--有省份id，那么判断市的id。
            if(_city_id==undefined||_city_id==null||_city_id==""){
                return _prov_name;
            }
            //--有市id，那么判断乡镇id。
            if(_country_id==undefined||_country_id==null||_country_id==""){
                return _prov_name+settings.separator+_city_name;
                return;
            }
            return _prov_name+settings.separator+_city_name+settings.separator+_country_name;
        }

    };
})(jQuery);