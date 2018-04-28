(function (ng) {

    var mod = ng.module("servicioModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/servicios/';

            $urlRouterProvider.otherwise("/serviciosList");

            $stateProvider.state('servicios', {
                url: '/servicios',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'servicios.html',
                        controller: 'servicioCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('serviciosList', {
                url: '/list',
                parent: 'servicios',
                views: {
                    'listView': {
                        templateUrl: basePath + 'servicios.list.html'
                    }
                }
            }).state('servicioDetail', {
                url: '/{servicioId:int}/detail',
                parent: 'servicios',
                param: {servicioId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'servicios.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'servicios.detail.html',
                        controller: 'servicioDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('servicioCreate', {
                url: '/create',
                parent: 'servicios',
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/servicios.new.html',
                        controller: 'servicioNewCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['admin']
                }
            }).state('servicioUpdate', {
                url: '/update/{servicioId:int}',
                parent: 'servicios',
                param: {
                    servicioId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/servicios.new.html',
                        controller: 'servicioUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['admin', 'assistant']
                }
            }).state('servicioDelete', {
                url: '/delete/{servicioId:int}',
                parent: 'servicio',
                param: {
                    servicioId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/servicios.delete.html',
                        controller: 'servicioDeleteCtrl'
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
