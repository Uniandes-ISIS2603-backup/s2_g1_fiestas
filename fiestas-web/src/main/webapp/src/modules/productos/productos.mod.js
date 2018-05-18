(function (ng) {
    var mod = ng.module("productosModule", ['ui.router']);
    mod.constant("productosContext", "productos");
    mod.constant("proveedoresContext", "api/proveedores");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/productos/';

            $urlRouterProvider.otherwise("/productosList");

            $stateProvider.state('productos', {
                url: '/productos',
                abstract: true,
                parent: 'proveedorDetail',
                views: {
                    'childrenView': {
                        templateUrl: basePath + 'productos.html',
                        controller: 'productosCtrl',
                        controllerAs: 'ctrl'
                    }
                },
                data: {
                    requireLogin: false,
                   roles: ['Admin','Proveedor','Cliente']
                }
            }).state('productosList', {
                url: '/list',
                parent: 'productos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'productos.list.html'
                    }
                }
            }).state('productosDetail', {
                url: '/{productoId:int}/detail',
                parent: 'productos',
                param:{productoId : null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'productos.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'productos.detail.html',
                        controller: 'productosDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state('productosCreate', {
                url: '/create',
                parent: 'productos',
                params:{productoId : null, proveedorId : null},
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/productos.new.html',
                        controller: 'productosNewCtrl'
                    }
                }
            }).state('productoUpdate', {
                url: '/update/{productoId:int}',
                parent: 'productos',
                param: {
                    productoId: null, proveedorId : null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/productos.new.html',
                        controller: 'productoUpdateCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor']
                }
            }).state('productoDelete', {
                url: '/delete/{productoId:int}',
                parent: 'productos',
                param: {
                    productoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/delete/productos.delete.html',
                        controller: 'productoDeleteCtrl'
                    }
                },
                data: {
                    requireLogin: true,
                   roles: ['Admin','Proveedor']
                }
            }).state('productoAll', {
                url: '/{productoId:int}/detail',
                views: {
                    'detailView': {
                        templateUrl: basePath + 'productos.detail.html',
                        controller: 'productosAllCtrl',
                        controllerAs: 'ctrl'
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

