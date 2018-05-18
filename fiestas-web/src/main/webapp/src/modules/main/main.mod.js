/**
 * @ngdoc overview
 * @name main.module:loginModule
 * @description
 * Definición del módulo de Angular de Manejo de Pagina Principal.
 * Los estados definidos en este modulo son:
 * ```
 * | ESTADO           | URL                        | VISTAS                 |
 * |------------------|----------------------------|------------------------|
 * | main             | /main                      | mainView:              |
 * |                  |                            | main.html              |
 * |------------------|----------------------------|------------------------|
 *```
 */
(function (ng) {
    // Definición del módulo
    var mod = ng.module("mainModule", ['ui.router']);

    // Configuración de los estados del módulo
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/main/';

            $urlRouterProvider.otherwise("/main");


            $stateProvider.state('main', {
                url: '/main',
                data: {
                    requireLogin: false
                },
                views: {
                    'mainView': {
                        templateUrl: basePath + 'main.html',
                        controller: 'mainCtrl'
                    }
                }
            }).$stateProvider.state('contactanos', {
                url: '/contacto',
                data: {
                    requireLogin: false
                },
                views: {
                    'mainView': {
                        templateUrl: basePath + 'contactanos.html'
                    }
                }
            });
        }
    ]);
})(window.angular);