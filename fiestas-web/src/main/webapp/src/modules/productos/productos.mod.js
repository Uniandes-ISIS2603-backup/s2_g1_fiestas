/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function (ng) {
    
    

    var mod = ng.module("productoModule", ['ui.router']);
    
   mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/productos/';

            $urlRouterProvider.otherwise("/productosList");

            $stateProvider.state('productos', {
                url: '/productos',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'productos.html',
                        controller: 'productoCtrl',
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
            }).state('productoDetail', {
                url: '/{productoId:int}/detail',
                parent: 'producto',
                param: {productoId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'productos.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'productos.detail.html',
                        controller: 'productoDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            });
        }
    
]);
})(window.angular);

