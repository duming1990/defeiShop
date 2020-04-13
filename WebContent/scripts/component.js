Vue.component('dynamic-comment-item', {
	// 声明 props
	props: ['item','ctx','is_show_huifu','showindex'],
	methods: {
		openUser: function(add_user_id) {
			 var link_url = this.ctx + 'm/MUserCenter.do?method=index&user_id='+add_user_id;
             location.href=link_url;
		},
		goLiuYan: function(id, type,index,flag) {
			$(".replaymsg").show();
			$(".replaymsg textarea").focus();
			$(".replaymsg textarea").attr("name", "comment" + id);
			$(".replaymsg textarea").attr("id", "comment" + id);
			$("#comment_dyna_id").val(id);
			$("#comment_dyna_type").val(type);
			$("#comment_dyna_index").val(index);
			$("#comment_dyna_flag").val(flag);
		},
		showCommentAll:function(count,event){
			 var el = event.currentTarget;
			 if(count == this.showindex){
				 this.showindex = 3;
				 el.innerHTML="共"+count+"条回复";
				
			 }else{
				 this.showindex = count;
				 el.innerHTML="收起";
			 }
			 
			
		}
	},
	template: '<div><div><div v-for="(itemComment,index) in item.map.commentList">'+
	'<div class="center-content25" v-show="index < showindex">'+
	'<div class="center-content251 center-content251-a">'+
	'<div class="center-content2511" @tap="openUser(itemComment.add_user_id);">'+
	'<img class="center-content2512" v-if="null != itemComment.map.user_logo && itemComment.map.user_logo.indexOf(\'http://\') == -1" :src="ctx + itemComment.map.user_logo">'+
	'<img class="center-content2512" v-else-if="null != itemComment.map.user_logo && itemComment.map.user_logo.indexOf(\'http://\') != -1" :src="itemComment.map.user_logo">'+
	'<img class="center-content2512" v-else src="${ctx}/styles/images/user.png" />'+
	'<span class="center-content25121">{{itemComment.map.real_name}}</span>'+
	'</div>'+
	'<div class="center-content2512">'+
	'<p class="center-content25123">@{{itemComment.link_user_name}}:{{itemComment.content}}</p>'+
	'<p>'+
	
//	'<span>{{itemComment.add_date | formatDate}}</span>'+
	
	'<span v-if="is_show_huifu == 1" class="huifu" :id="\'liuyan_comment\'+itemComment.id" @click="goLiuYan(itemComment.id,2,index,1)"><span></span></span>'+
	'</p>'+
	'</div>'+
	'</div>'+
	'</div>'+
	'</div>'+
	'<div v-show="item.map.commentList.length >= showindex" class="dynamic-comment-length" @tap="showCommentAll(item.map.commentList.length,$event)">共{{item.map.commentList.length}}条回复</div>'+
	'</div>'
})

Vue.component('tab-list', {
	props: ['tab_active', 'tablist'],
	methods: {
		entpTabClick: function(tabIndex) {
			this.tab_active = tabIndex;
			this.$emit("sontabclick", tabIndex);
		}
	},
	template:'<div class="inner mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">'+
	'<div class="cuncate swiper-wrapper" id="cuncate" >'+
	'<div style="width: 23%;display: inline-block;" v-for="item,index in tablist" @tap="entpTabClick(index)">'+
	'<a :href="\'#itemcontent\' + index" class="item mui-control-item" :class="{\'mui-active \' : tab_active == index}">{{item}}</a>'+
	'</div>'+
	'</div>'+
	'</div>'
})


Vue.component('header-item', {
	// 声明 props
  	props: ['header_title', 'canback', 'showrightbutton'],
  	methods: {
		rightButtonClick: function() {
			this.$emit("buttonclick");
		}
	},
	template: '<header class="mui-bar mui-bar-nav">'
			  +'<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"  v-if="canback"></a>'
			  +'<h1 id="title" class="mui-title">{{header_title}}</h1>'
			  +'<button class="mui-btn mui-btn-blue mui-btn-link mui-pull-right" v-if="showrightbutton" @tap="rightButtonClick">{{showrightbutton}}</button>'
	 		  +'</header>' 
})
