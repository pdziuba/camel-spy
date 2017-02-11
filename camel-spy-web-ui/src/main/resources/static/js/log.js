function onClickExpandLogTable($event){

        let hiddenClass = "fa-plus-square-o";
        let shownClass = "fa-minus-square-o";
        let rowClass = "log-table-row--hidden";

        let button = $($event.target).closest(".js-expand-log-button");
        let row = button.closest("tr").next(".log-table-row");

        let buttonIconElement = button.children("i");

        row.toggleClass(rowClass);
        buttonIconElement.toggleClass(hiddenClass);
        buttonIconElement.toggleClass(shownClass);

}