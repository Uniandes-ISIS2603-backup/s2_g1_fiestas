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
                        controller: 'valoracionesCtrl',
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
                },
                 data: {
                    requireLogin: true,
                    roles: ['admin']
                }
            }).state('valoracionCreate', {
                url: '/create',
                parent: 'valoraciones',
                views: {
                    'listView': {
                        templateUrl: basePath + '/new/valoraciones.new.html',
                        controller: 'valoracionNewCtrl'
                    }
                }
            }).state('valoracionUpdate', {
                url: '/update/{valoracionId:int}',
                parent: 'valoraciones',
                param: {
                    valoracionId: null
                },
                views: {
                    'listView': {
                        templateUrl: basePath + '/new/valoraciones.new.html',
                        controller: 'valoracionUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['admin', 'assistant']
                }
            }).state('valoracionDelete', {
                url: '/delete/{valoracionId:int}',
                parent: 'valoracion',
                param: {
                    valoracionId: null
                },
                views: {
                    'listView': {
                        templateUrl: basePath + '/delete/valoraciones.delete.html',
                        controller: 'valoracionDeleteCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['admin']
                }
            });
        }
    ]);
})(window.angular);
