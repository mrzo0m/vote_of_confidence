Ext.define('VocApp.view.viewport.ViewportController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.viewport',

    listen : {
        controller : {
            '#' : {
                unmatchedroute : 'onRouteChange'
            }
        }
    },


    routes: {
        ':node': 'onRouteChange'
    },


    collapsedCls: 'main-nav-collapsed',


    onRouteChange: function () {
        var hashTag = Ext.History.getToken();
        this.showView(hashTag);
    },

    onLaunch: function() {
        this.hideNavi();
    },


    config: {
        selection: null,
        showNavigation: false
    },

    onToggleNavigationSize: function () {
        this.setShowNavigation(!this.getShowNavigation());
    },

    onMenuBarClick: function () {
        var view = this.getView(),
            navigationTree = view.lookup('navigationTree');

        if (!this.getShowNavigation()) {
            navigationTree.hide()
        } else {
            navigationTree.show();
        }
        this.onToggleNavigationSize();
    },

    toolbarButtonClick: function (btn) {
        var href = btn.config.href;

        this.redirectTo(href, {replace: true});
    },


    hideNavi: function () {
        let navigationTree = this.getView().lookup('navigationTree');
        if (!this.getShowNavigation()) {
            navigationTree.hide()
        }
        this.onToggleNavigationSize();

    },

    showView: function(hashTag) {
        var mainCard = this.lookupReference('mainCard'),
            hashTag = (hashTag || '').toLowerCase();
        mainCard.pop();
        var item = mainCard.push({
            xtype: hashTag,
            routeId: hashTag
        });
        mainCard.setActiveItem(item);
        this.hideNavi();
    },


    onNavigationItemClick: function () {
        // The phone profile's controller uses this event to slide out the navigation
        // tree. We don't need to do anything but must be present since we always have
        // the listener on the view...
    },

    onNavigationTreeSelectionChange: function (tree, node) {
        var to = node && (node.get('routeId') || node.get('viewType'));
        if (to) {
            this.redirectTo(to, {replace: true});
        }
    }

});

