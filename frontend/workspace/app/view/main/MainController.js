/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 */
Ext.define('VocApp.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.main',

    listen: {
        controller: {
            '#': {
                unmatchedroute: 'setCurrentView'
            }
        }
    },

    routes: {
        ':node': 'setCurrentView'
    },

    config: {
        showNavigation: false
    },

    onNavigationItemClick: function () {
        // The phone profile's controller uses this event to slide out the navigation
        // tree. We don't need to do anything but must be present since we always have
        // the listener on the view...
    },

    onNavigationTreeSelectionChange: function (tree, node) {
        var to = node && (node.get('routeId') || node.get('viewType'));

        if (to) {
            this.redirectTo(to);
        }
    },

    setCurrentView: function (hashTag) {
        //TODO: This is stub for routing pages - fix ASAP
        var routesMap = new Ext.util.HashMap();
        routesMap.add('agreement', 'agreement');
        hashTag = (hashTag || '').toLowerCase();
        var view = this.getView(),
            navigationTree = this.lookup('navigationTree'),
            store = navigationTree.getStore(),
            node = store.findNode('routeId', hashTag) ||
                store.findNode('viewType', hashTag),
            item = view.child('component[routeId=' + hashTag + ']');

        if (!item && node && hashTag) {
            item = {
                xtype: node.get('viewType'),
                routeId: hashTag
            };
        } else if (!item && !node && hashTag) {
            if (routesMap.contains(hashTag)) {
                this.redirectTo(routesMap.get(hashTag));
                return;
            }
            this.redirectTo('home');
            return;
        }

        view.setActiveItem(item);

        navigationTree.setSelection(node);
        this.onMenuBarClick();
    },

    onToggleNavigationSize: function () {
        this.setShowNavigation(!this.getShowNavigation());
    },


    onMenuBarClick: function () {
        var view = this.getView(),
            navigationTree = this.lookup('navigationTree');
        if (!this.getShowNavigation()) {
            navigationTree.hide()
        } else {
            navigationTree.show();
        }
        this.onToggleNavigationSize();
    },

    toolbarButtonClick: function (btn) {
        var href = btn.config.href;

        this.redirectTo(href);
    }


});
