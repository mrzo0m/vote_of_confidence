/**
 * Created by 16678596 on 28.03.2019.
 */
Ext.define('VocApp.view.main.ToolbarController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.toolbar',

    toolbarButtonClick: function (btn) {
        var href = btn.config.href;

        this.redirectTo(href);
    }
});