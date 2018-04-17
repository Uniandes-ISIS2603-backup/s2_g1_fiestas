(function (ng) {

    var mod = ng.module("valoracionModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/valoraciones/';

            $urlRouterProvider.otherwise("/valoracionesList");

            $stateProvider.state('valoraciones', {
                url: '/valoraciones',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'valoraciones.html',
                        controller: 'valoracionCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('valoracionesList', {
                url: '/list',
                parent: 'valoraciones',
                views: {
                    'listView': {
                        templateUrl: basePath + 'valoraciones.list.html'
                    }
                }
            }).state('valoracionDetail', {
                url: '/{valoracionId:int}/detail',
                parent: 'valoraciones',
                param: {valoracionId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'valoraciones.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'valoraciones.detail.html',
                        controller: 'valoracionDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            });
        }
    ]);
})(window.angular);
