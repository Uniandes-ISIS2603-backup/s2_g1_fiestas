(function (ng) {

    var mod = ng.module("blogsModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/blogs/';

            $urlRouterProvider.otherwise("/blogsList");

            $stateProvider.state('blogs', {
                url: '/blogs',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'blogs.html',
                        controller: 'blogsCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('blogsList', {
                url: '/list',
                parent: 'blogs',
                views: {
                    'listView': {
                        templateUrl: basePath + 'blogs.list.html'
                    }
                }
            }).state('blogsDetail', {
                url: '/{blogsId:int}/detail',
                parent: 'blogs',
                param: {blogsId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'blogs.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'blogs.detail.html',
                        controller: 'blogsDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('blogsCreate', {
                url: 'create',
                parent: 'blogs',
                param: {blogsId: null},
                views: {
                    'detailView': {
                        templateUrl: basePath + '/create/blogs.new.html',
                        controller: 'blogsNewCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('blogsUpdate', {
                url: '/{blogsId:int}/update',
                parent: 'blogs',
                param: {blogsId: null},
                views: {
                    'detailView': {
                        templateUrl: basePath + '/create/blogs.new.html',
                        controller: 'blogsUpdateCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('blogsDelete', {
                url: '/{blogsId:int}/delete',
                parent: 'blogs',
                param: {blogsId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + '/delete/blogs.delete.html',
                        controller: 'blogsDeleteCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            });
        }
    ]);
})(window.angular);
