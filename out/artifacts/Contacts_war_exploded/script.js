
var xhttp = getXHR();
var page_size = 1;//总页面数
var page_now = 1;//当前页面
var add_or_edit;//添加联系人(-1)还是修改联系人(id)

showContacts(1);

window.onload = function () {
    addEventListener();
};

function addEventListener() {
    document.getElementById("first_page").onclick = function () {
        if (page_now===1){
            return;
        }
        showContacts(1);
    };
    document.getElementById("pre_page").onclick = function () {
        if (page_now===1){
            return;
        }
        showContacts(page_now-1)
    };
    document.getElementById("next_page").onclick = function () {
        if (page_now===page_size){
            return;
        }
        /**
         * 。。。。。。。
         */
        // showContacts(page_now+1);
        var page = page_now-1+2;
        showContacts(page)
    };
    document.getElementById("last_page").onclick = function () {
        if (page_now===page_size){
            return;
        }
        showContacts(page_size);
    };
    document.getElementById("select_page").onchange = function() {
        showContacts(document.getElementById("select_page").value);
    };
    document.getElementById("add_contact").onclick = function () {
        add_or_edit = -1;
        forbidOther();
        showAddForm();
    };
    document.getElementById("close").onclick = function () {
        hideAddForm();
        showOther();
    };
    document.getElementById("submitInfo").onclick = function (event) {
        doAddSubmit(add_or_edit, event);
    };
    document.getElementById("search_contact").onclick = function () {
        forbidOther();
        showSearchForm();
    };
    document.getElementById("close_search").onclick = function () {
        hideSearchForm();
        showOther();
    };
    document.getElementById("submitSearch").onclick = function (event) {
        doSearchSubmit(event);
    };
    document.getElementById("empty").onclick = function () {
        hideSearchForm();
        hideAddForm();
        hideResultTable();
        showOther();
    }
}

function doSearchSubmit(event) {
    var form = document.forms.searchInf;
    var namePattern = new RegExp("[a-zA-Z]{2,30}|[\u4e00-\u9fa5]{2,30}");
    var numberPattern = new RegExp("[U][2][0]\\d{7}");
    var searchBy = document.getElementById("searchBy");
    if (!form.input.value){
        alert("请输入姓名或学号");
        form.input.focus();
    } else if ((searchBy.value==="by_name")) {
        if (!namePattern.test(form.input.value)){
            alert('请输入正确的姓名');
            form.input.focus();
        } else {
            doSearchByName(form.input.value);
        }
    } else if ((searchBy.value==="by_num")) {
        if (!numberPattern.test(form.input.value)) {
            alert('请输入正确的学号');
            form.input.focus();
        } else {
            doSearchByNum(form.input.value);
        }
    }
    forbiddenEvent(event);
}

function doSearchByName(name) {
    xhttp.open("GET","SearchByName?name="+name,true);
    xhttp.onreadystatechange = function () {
        if((xhttp.status === 200)&&(xhttp.readyState === 4)){
            var result = xhttp.responseText;
            var json = JSON.parse(result);
            var status = json.status;

            switch (status){
                case 0:
                    clearItems("table_show_result");
                    hideSearchForm();
                    showSearchContacts(json.data);
                    break;
                case 1:
                    console.error("系统出错，请稍后尝试");
                    location.assign("error.jsp");
                    break;
                case 2:
                    alert("请求不合法");
                    break;
                case 3:
                    alert("参数不完整!");
                    break;
                default:
                    break;
            }
        }
    };
    xhttp.send();
}

function doSearchByNum(number) {
    xhttp.open("GET","SearchByNum?number="+number,true);
    xhttp.onreadystatechange = function () {
        if((xhttp.status === 200)&&(xhttp.readyState === 4)){
            var result = xhttp.responseText;
            var json = JSON.parse(result);
            var status = json.status;

            switch (status){
                case 0:
                    clearItems("table_show_result");
                    hideSearchForm();
                    showSearchContacts(json.data);
                    break;
                case 1:
                    console.error("系统出错，请稍后尝试");
                    location.assign("error.jsp");
                    break;
                case 2:
                    alert("请求不合法");
                    break;
                case 3:
                    alert("参数不完整!");
                    break;
                default:
                    break;
            }
        }
    };
    xhttp.send();
}

function showSearchContacts(contacts) {
    var size = contacts.length;
    if (size===0||contacts[0].id===-1){
        alert("找不到相关信息。");
        return;
    }
    showResultTable();
    for (var i = 0; i < size; i++) {
        addItem(contacts[i], "table_show_result");
    }
}

function doAddSubmit(id, event) {
    var form = document.forms.addInf;
    var namePattern = new RegExp("[a-zA-Z]{2,30}|[\u4e00-\u9fa5]{2,30}");
    var numberPattern = new RegExp("[U][2][0]\\d{7}");
    var phonePattern = new RegExp("(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?|0{0,1}1[3|4|5|6|7|8|9][0-9]{9}");
    if (!form.name.value) {
        alert('请输入姓名');
        form.name.focus();
    } else if (!namePattern.test(form.name.value)){
        alert('请输入正确的姓名');
        form.name.focus();
    } else if (!form.number.value) {
        alert('请输入学号');
        form.number.focus();
    } else if (!numberPattern.test(form.number.value)) {
        alert('请输入正确的学号');
        form.number.focus();
    } else if (!form.phone.value) {
        alert('请输入手机号码');
        form.phone.focus();
    } else if (!phonePattern.test(form.phone.value)) {
        alert('请输入正确的手机号码');
        form.phone.focus();
    } else {
        console.info("姓名:"+form.name.value+"  学号:"+form.number.value+"  电话:"+form.phone.value);
        if (id === -1) {
            doAddContact(form.name.value, form.number.value, form.phone.value);
        } else {
            doEditContact(id, form.name.value, form.number.value, form.phone.value);
        }
    }
    forbiddenEvent(event);
}

function doAddContact(name, number, phone) {
    xhttp.open("GET","AddContact?name="+name+"&number="+number+"&phone="+phone,true);
    xhttp.onreadystatechange = function () {
        if((xhttp.status === 200)&&(xhttp.readyState === 4)){
            var result = xhttp.responseText;
            var json = JSON.parse(result);
            var status = json.status;

            switch (status){
                case 0:
                    page_size = json.page_size;
                    hideAddForm();
                    showContacts(page_size);
                    showOther();
                    break;
                case 1:
                    console.error("系统出错，请稍后尝试");
                    location.assign("error.jsp");
                    break;
                case 2:
                    alert("请求不合法，该学号已经存在!");
                    break;
                case 3:
                    alert("参数不完整!");
                    break;
                default:
                    break;
            }
        }
    };
    xhttp.send();
}

function doEditContact(id, name, number, phone) {
    xhttp.open("GET","UpdateContact?id="+id+"&name="+name+"&number="+number+"&phone="+phone,true);
    xhttp.onreadystatechange = function () {
        if((xhttp.status === 200)&&(xhttp.readyState === 4)){
            var result = xhttp.responseText;
            var json = JSON.parse(result);
            var status = json.status;

            switch (status){
                case 0:
                    hideAddForm();
                    showContacts(page_now);
                    showOther();
                    break;
                case 1:
                    console.error("系统出错，请稍后尝试");
                    location.assign("error.jsp");
                    break;
                case 2:
                    alert("请求不合法，该学号已经存在!");
                    break;
                case 3:
                    alert("参数不完整!");
                    break;
                default:
                    break;
            }
        }
    };
    xhttp.send();
}

function forbiddenEvent(event) {
    event = event || window.event;
    if (event.stopPropagation){
        event.stopPropagation();
    } else{
        event.cancelBubble = true;
    }
    if (event.preventDefault){
        event.preventDefault();
    } else{
        event.returnValue = false;
    }
}

/**
 * 将empty元素宽高置0，使其他元素置于顶层
 * @constructor
 */
function showOther() {
    var empty = document.getElementById("empty");
    empty.style.position = "absolute";
    empty.style.width = 0;
    empty.style.height = 0;
    empty.style.top = 0;
    empty.style.left = 0;
}

/**
 * 设置empty元素的宽高，使其覆盖其他元素
 */
function forbidOther() {
    var empty = document.getElementById("empty");
    empty.style.position = "absolute";
    empty.style.width = document.body.clientWidth-1;
    empty.style.height = document.body.clientHeight-1;
    empty.style.top = 0;
    empty.style.left = 0;
}

function showAddForm() {
    document.forms.addInf.style.zIndex = "3";
    document.forms.addInf.style.opacity = "1";
    document.forms.addInf.style.top = "50%";
}

function hideAddForm() {
    document.forms.addInf.style.zIndex = "0";
    document.forms.addInf.style.opacity = "0";
    document.forms.addInf.style.top = "-300px";
    document.getElementById("name").value = "";
    document.getElementById("name").placeholder = "姓名";
    document.getElementById("number").value = "";
    document.getElementById("number").placeholder = "学号";
    document.getElementById("phone").value = "";
    document.getElementById("phone").placeholder = "手机";
}

function showSearchForm() {
    document.forms.searchInf.style.zIndex = "3";
    document.forms.searchInf.style.opacity = "1";
    document.forms.searchInf.style.top = "50%";
}

function hideSearchForm() {
    document.forms.searchInf.style.zIndex = "0";
    document.forms.searchInf.style.opacity = "0";
    document.forms.searchInf.style.top = "-300px";
}

function showResultTable() {
    document.getElementById("table_show_result").style.zIndex = "3";
    document.getElementById("table_show_result").style.opacity = "1";
    document.getElementById("table_show_result").style.top = "50%";
}

function hideResultTable() {
    document.getElementById("table_show_result").style.zIndex = "0";
    document.getElementById("table_show_result").style.opacity = "0";
    document.getElementById("table_show_result").style.top = "-300px";
}

//获取XHR对象
function getXHR() {
    var xhttp;
    if(window.XMLHttpRequest){
        xhttp = new XMLHttpRequest();
    } else {
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xhttp;
}

function showContacts(page) {
    if (page>page_size||page<1){
        return;
    }
    xhttp.open("GET","ShowContacts?page_now="+page,true);
    xhttp.onreadystatechange = function () {
        if((xhttp.status === 200)&&(xhttp.readyState === 4)){
            var result = xhttp.responseText;
            var json = JSON.parse(result);
            var status = json.status;

            clearItems("table_show");

            switch (status){
                case 0:
                    page_size = json.page_size;
                    page_now = page;
                    set_select_page(page_size, page_now);
                    var data = json.data;
                    var size = data.length;
                    for (var i = 0; i < size; i++) {
                        addItem(data[i], "table_show");
                    }
                    break;
                case 1:
                    console.error("系统出错，请稍后尝试");
                    location.assign("error.jsp");
                    break;
                case 2:
                    alert("请求不合法");
                    break;
                case 3:
                    alert("参数不完整!");
                    break;
                default:
                    break;
            }
        }
    };
    xhttp.send();
}

function addItem(contact,id) {
    var tr = document.createElement("tr");
    var td_name = document.createElement("td");
    var td_num = document.createElement("td");
    var td_phone = document.createElement("td");
    var td_edit = document.createElement("td");
    var td_delete = document.createElement("td");

    var img_edit = document.createElement("img");
    var img_delete = document.createElement("img");
    img_edit.src = "images/edit_hover.png";
    img_delete.src = "images/delete_hover.png";
    img_edit.style.cursor = "pointer";
    img_delete.style.cursor = "pointer";
    img_edit.onclick = function () {
        edit_contact(contact.id, contact.name, contact.number, contact.phone);
    };
    img_delete.onclick = function () {
        delete_contact(contact.id);
    };

    td_name.title = "姓名";
    td_num.title = "学号";
    td_phone.title = "电话";
    td_edit.title = "更改";
    td_delete.title = "删除";

    td_name.appendChild(document.createTextNode(contact.name));
    td_num.appendChild(document.createTextNode(contact.number));
    td_phone.appendChild(document.createTextNode(contact.phone));
    td_edit.appendChild(img_edit);
    td_delete.appendChild(img_delete);

    tr.id = contact.id;
    tr.appendChild(td_name);
    tr.appendChild(td_num);
    tr.appendChild(td_phone);
    tr.appendChild(td_edit);
    tr.appendChild(td_delete);
    document.getElementById(id).appendChild(tr);
}

function edit_contact(id, name, number, phone) {
    if (!confirm("确定修改该联系人信息？")){
        return;
    }
    hideResultTable();
    forbidOther();
    document.getElementById("name").value = name;
    document.getElementById("number").value = number;
    document.getElementById("phone").value = phone;
    showAddForm();
    add_or_edit = id;
}

function delete_contact(id) {
    if (!confirm("确定删除该联系人？")){
        return;
    }
    hideResultTable();
    xhttp.open("GET","DeleteContact?id="+id,true);
    xhttp.onreadystatechange = function () {
        if((xhttp.status === 200)&&(xhttp.readyState === 4)){
            var result = xhttp.responseText;
            var json = JSON.parse(result);
            var status = json.status;

            switch (status){
                case 0:
                    page_size = json.page_size;
                    var contact_size = json.contact_size;
                    if (contact_size % 10 === 0) {
                        page_now--;
                    }
                    set_select_page(page_size, page_now);
                    showContacts(page_now);
                    break;
                case 1:
                    console.error("系统出错，请稍后尝试");
                    location.assign("error.jsp");
                    break;
                case 2:
                    alert("请求不合法");
                    break;
                case 3:
                    alert("参数不完整!");
                    break;
                default:
                    break;
            }
        }
    };
    xhttp.send();
}

function clearItems(id) {
    var table = document.getElementById(id);
    var children = table.children;
    if (children.length === 0){
        return;
    }
    for (var i=children.length-1;i>=0;i--){
        table.removeChild(children[i]);
    }
}

/**
 * 设置select
 * @param page_sum
 * @param page
 */
function set_select_page(page_sum, page) {
    var select = document.getElementById("select_page");
    // var child = select.childNodes;
    var child = select.children;
    for (var i=child.length-1;i>=0;i--){
        select.removeChild(child[i]);
    }

    for (var j=1;j<=page_sum;j++){
        var option = document.createElement("option");
        var option_text = document.createTextNode(j);
        option.value = j;
        option.appendChild(option_text);
        select.appendChild(option);
    }
    child[page-1].selected = true;
}