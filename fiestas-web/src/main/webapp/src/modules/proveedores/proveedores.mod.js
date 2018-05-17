(function (ng) {

    var mod = ng.module("proveedorModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/proveedores/';

            $urlRouterProvider.otherwise("/proveedoresList");

            $stateProvider.state('proveedores', {
                url: '/proveedores',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'proveedores.html',
                        controller: 'proveedorCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Admin','Proveedor']
                }
            }).state('proveedoresList', {
                url: '/list',
                parent: 'proveedores',
                views: {
                    'listView': {
                        templateUrl: basePath + 'proveedores.list.html'
                    }
                }
            }).state('proveedorDetail', {
                url: '/{proveedorId:int}/detail',
                parent: 'proveedores',
                param: {proveedorId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'proveedores.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'proveedores.detail.html',
                        controller: 'proveedorDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Admin','Proveedor']
                }

            }).state('proveedoresCreate', {
                url: '/create',
                parent: 'proveedores',
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/proveedores.new.html',
                        controller: 'proveedorNewCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Admin']
                }
            }).state('proveedorUpdate', {
                url: '/update/{proveedorId:int}',
                parent: 'proveedores',
                param: {
                    proveedorId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/proveedores.new.html',
                        controller: 'proveedorUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Admin', 'Proveedor']
                }
            }).state('proveedorDelete', {
                url: '/delete/{proveedorId:int}',
                parent: 'proveedores',
                param: {
                    proveedorId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/proveedores.delete.html',
                        controller: 'proveedorDeleteCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                    roles: ['Admin','Proveedor']
                }
            });
        }
    ]);
})(window.angular);
