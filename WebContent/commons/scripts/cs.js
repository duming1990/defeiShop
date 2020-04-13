<!--
/*
   Copyright 2007 KimSoft

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
 
/**
 * setup CascadeSelect
 * @param   cascadeSelect select object
 * @param   parent data index of parent
 * @param   nodes array contains data with structure [[parent, text, value],[],...[]]
 * @param   [optional] is onchange
 * @version build 20061229
 * @author  KimSoft
 */
function setupCascadeSelect(cascadeSelect, parent, nodes, isOnChange) {
	isOnChange = isOnChange || false;
	
    cascadeSelect.onchange = function () {
        setupCascadeSelect(this, this.options[this.selectedIndex].value, nodes, true);
    };
    cascadeSelect.getAttr = function (attrName) {
        return this[attrName] ? this[attrName] : this.getAttribute(attrName);
    };
    cascadeSelect.getElementById = function (id) {
        return this.form.elements[id] ? this.form.elements[id] : document.getElementById(id);
    };
    cascadeSelect.setDisplayStyle = function (value) {
        if (!this.multiple) {
            this.style.display = value;
        }
        var subElement = this.getElementById(this.getAttr("subElement"));
        if (subElement != null) {
            subElement.setDisplayStyle = this.setDisplayStyle;
        }
    };
    nodes.getChildNodesByParent = function (parent) {
        var childNodes = new Array();
        if (parent + "" == "") {
            return childNodes;
        }
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i][0] != null && nodes[i][0] == parent) {
                childNodes[childNodes.length] = nodes[i];
            }
        }
        return childNodes;
    };
    if (!isOnChange) {
        cascadeSelect.options.length = 0;
        var defaultText = cascadeSelect.getAttr("defaultText");
        var defaultValue = cascadeSelect.getAttr("defaultValue");
        var selectedValue = cascadeSelect.getAttr("selectedValue");
        if (defaultText != null && defaultValue != null) {
            cascadeSelect.options[cascadeSelect.options.length] = new Option(defaultText, defaultValue);
        }
        var childNodes = nodes.getChildNodesByParent(parent);
        for (var i = 0; i < childNodes.length; i++) {
            cascadeSelect.options[cascadeSelect.options.length] = new Option(childNodes[i][1], childNodes[i][2]);
            if (selectedValue != null && selectedValue == childNodes[i][2]) {
                cascadeSelect.selectedIndex = cascadeSelect.options.length - 1;
            }
        }
    }
    if (cascadeSelect.options.length > 0) {
        cascadeSelect.setDisplayStyle("");
        var subElement = cascadeSelect.getElementById(cascadeSelect.getAttr("subElement"));
        if (subElement != null) {
            setupCascadeSelect(subElement, cascadeSelect.options[cascadeSelect.selectedIndex].value, nodes, false);
        }
    } else {
        cascadeSelect.setDisplayStyle("none");
    }
}

/**
 * implement the CascadeSelect with "JavaScript Class"
 * @param   cascadeSelect select object
 * @param   parent data index of parent
 * @param   nodes array contains data with structure [[parent, text, value],[],...[]]
 * @version build 20061229
 * @author  KimSoft
 */
function CascadeSelect(element, parent, nodes) {
    this.form = element.form;
    this.nodes = nodes;
    this.attributes = {"subElement":"subElement", "defaultText":"defaultText", "defaultValue":"defaultValue", "selectedValue":"selectedValue"};
    this.build(element, parent, false);
}
CascadeSelect.prototype.getElementById = function (id) {
    return this.form.elements[id] ? this.form.elements[id] : document.getElementById(id);
};
CascadeSelect.prototype.getAttribute = function (element, attrName) {
    return element[attrName] ? element[attrName] : element.getAttribute(attrName);
};
CascadeSelect.prototype.getChildNodesByParent = function (parent) {
    var childNodes = new Array();
    if (parent + "" == "") {
        return childNodes;
    }
    for (var i = 0; i < this.nodes.length; i++) {
        if (this.nodes[i][0] != null && this.nodes[i][0] == parent) {
            childNodes[childNodes.length] = this.nodes[i];
        }
    }
    return childNodes;
};
CascadeSelect.prototype.setDisplayStyle = function (element, value) {
    var cs = this;
    if (!element.multiple) {
        element.style.display = value;
    }
    var subElement = this.getElementById(this.getAttribute(element, this.attributes["subElement"]));
    if (subElement != null) {
        subElement.setDisplayStyle = function () {
            cs.setDisplayStyle;
        };
    }
};
CascadeSelect.prototype.build = function (element, parent, isOnChange) {
    var cs = this;
    element.onchange = function () {
        cs.build(this, this.options[this.selectedIndex].value, true);
    };
    if (!isOnChange) {
        element.options.length = 0;
        var defaultText = this.getAttribute(element, this.attributes["defaultText"]);
        var defaultValue = this.getAttribute(element, this.attributes["defaultValue"]);
        var selectedValue = this.getAttribute(element, this.attributes["selectedValue"]);
        if (defaultText != null && defaultValue != null) {
            element.options[element.options.length] = new Option(defaultText, defaultValue);
        }
        var childNodes = this.getChildNodesByParent(parent);
        for (var i = 0; i < childNodes.length; i++) {
            element.options[element.options.length] = new Option(childNodes[i][1], childNodes[i][2]);
            if (selectedValue != null && selectedValue == childNodes[i][2]) {
                element.selectedIndex = element.options.length - 1;
            }
        }
    }
    if (element.options.length > 0) {
        this.setDisplayStyle(element, "");
        var subElement = this.getElementById(this.getAttribute(element, this.attributes["subElement"]));
        if (subElement != null) {
            this.build(subElement, element.options[element.selectedIndex].value, false);
        }
    } else {
        this.setDisplayStyle(element, "none");
    }
};
//-->

