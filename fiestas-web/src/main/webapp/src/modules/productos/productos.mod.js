(function (ng) {
    var mod = ng.module("productosModule", ['ui.router']);

   mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/productos/';

            $urlRouterProvider.otherwise("/productosList");

            $stateProvider.state('productos', {
                url: '/productos',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'productos.html',
                        controller: 'productosCtrl',
                        controllerAs: 'ctrl'
                    }
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
                param: {productoId: null},
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
                    productoId: null
                },
                views: {
                    'detailView': {
                        templateUrl: basePath + '/new/productos.new.html',
                        controller: 'productoUpdateCtrl'
                    }
                }
            });
        }
    
]);
})(window.angular);

