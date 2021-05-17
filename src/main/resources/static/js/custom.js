var count = 1;
var section;
  function duplicate(node) {
    var elem = node.parentNode.parentNode;
    var clone = elem.cloneNode(true);
    clone.id = 'datalist-input-' + (++count) ;
    elem.after(clone);
    clone.querySelector(".form-control").value = "";
    clone.querySelector(".form-control").focus;
    clone.querySelector(".form-control").setActive;
    clone.querySelector(".form-control").select();

    clone.querySelector(".btn-danger").style.display = 'inline';
    elem.querySelector(".btn-danger").style.display = 'inline';
    node.style.display = 'none';
  }

  function remove(node) {
    let datalistField = node.parentNode.parentNode;
    let list = datalistField.parentNode;
    datalistField.remove();
    const fields = list.querySelectorAll(".item-datalist");

    if (fields[fields.length - 1].querySelector(".btn-success") != null) {
    fields[fields.length - 1].querySelector(".btn-success").style.display = "inline";
    if (fields.length == 1) {
      fields[0].querySelector(".btn-danger").style.display = "none";
    }
  }
  }

  function saveSection() {
    section = document.getElementsByClassName("row")[0].cloneNode(true);
  }

  function anotherOne(node) {
    let element = node.parentNode.parentNode.parentNode.parentNode.parentNode;
    element.querySelector(".btn-primary").style.display = "none";
    element.querySelector(".btn-secondary").style.display = "inline";
    section.querySelector(".btn-secondary").style.display = "inline";
    element.parentNode.appendChild(section);
    section = section.cloneNode(true);
  }

  function removeRow(node) {
    node.parentNode.parentNode.parentNode.parentNode.parentNode.remove();

    let fields = document.querySelectorAll(".row.sectionRow");
    fields[fields.length - 1].querySelector(".btn-primary").style.display = "inline";
    if (fields.length == 1) {
      fields[0].querySelector(".btn-secondary").style.display = "none";
    }
  }
  // var fieldList = document.getElementsByClassName("item-datalist");
  // for (let el of fieldList) {
  //   el.addEventListener("keydown", function (e) {
  //     if (e.code === "Enter") {  //checks whether the pressed key is "Enter"
  //       duplicate(this.firstChild);
  //     }
  // });
  // }