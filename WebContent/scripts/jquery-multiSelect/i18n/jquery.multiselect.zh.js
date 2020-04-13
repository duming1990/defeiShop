/* Spanish initialisation for the jQuery UI multiselect plugin. */
/* Written by Vinius Fontoura Correa(vinusfc@gmail.com). */

$.extend($.ech.multiselect.prototype.options, {
	checkAllText: '全选',
	uncheckAllText: '取消',
	noneSelectedText: '请选择...',
	selectedText: '# 个选项被选中',
	label:'过滤',
	placeholder:'输入关键字'
});
if (undefined != $.ech.multiselectfilter && $.ech.multiselectfilter) {
	$.extend($.ech.multiselectfilter.prototype.options, {
		label:'过滤：',
		placeholder:'输入关键字'
	});
}
