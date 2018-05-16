/**
 * @ngdoc overview
 * @name login.module:loginModule
 * @description
 * Definición del módulo de Angular de Manejo de sesión.
 * Los estados definidos en este modulo son:
 * ```
 * | ESTADO           | URL                        | VISTAS                 |
 * |------------------|----------------------------|------------------------|
 * | login            | /login                     | mainView:              |
 * |                  |                            | login.html             |
 * | logout           | /logout                    | listView:              |
 * |                  |                            | logout.html            |
 * |------------------|----------------------------|------------------------|
 *```
 */
(function (ng) {
    // Definición del módulo
    var mod = ng.module("signinModule", ['ui.router']);

    // Configuración de los estados del módulo
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/signin/';

            $urlRouterProvider.otherwise("/signin");


            $stateProvider.state('signin', {
                url: '/signin',
                data: {
                    requireLogin: false
                },
                views: {
                    'mainView': {
                        templateUrl: basePath + 'signin.html',
                        controller: 'signinCtrl'
                    }
                }
            });
        }
    ]);
})(window.angular);