
/**
 * @ngdoc overview
 * @name mainApp.module:mainApp
 * @description
 * Definición del módulo principal de Angular. El módulo encapsula todos los
 * módulos de la aplicación incluyendo los módulos externos que son injectados
 * dentro de otros módulos (como Bootstrap y el Router). Este módulo inicia la
 * aplicación al momento de ingresar a cualquier página de la aplicación por
 * primera vez.
 */
(function (ng) {
    var app = angular.module('mainApp', [
        // External dependencies
        'ui.router',
        'ui.bootstrap',
        // Internal modules dependencies       
        'proveedorModule',
        'clienteModule',
        'blogsModule',
        'bonosModule',
        'servicioModule',
        'valoracionModule',
        'pagoModule',
        'eventoModule',
        'productosModule',
        'tematicasModule',
        'contratoModule',
        'loginModule',
        'signinModule',
        'horarioModule',
        'mainModule' 
        
    ]);
    // Resuelve problemas de las promesas
    app.config(['$qProvider', function ($qProvider) {
            $qProvider.errorOnUnhandledRejections(false);
        }]);

    app.run(['$rootScope', '$transitions', function ($rootScope, $transitions) {

            $transitions.onSuccess({to: '*'}, function (trans) {

                var $state = trans.router.stateService;
                var requireLogin = $state.current.data.requireLogin
                var roles = $state.current.data.roles
               

                /**
                 * @ngdoc function
                 * @name isAuthenticated
                 * @methodOf mainApp.module:mainApp
                 * @description Esta función define si el usuario se encuentra
                 * dentro de su cuenta.
                 * @returns {Boolean} Verdadero si está dentro de su cuenta.
                 */
                $rootScope.isAuthenticated = function () {

                    if (sessionStorage.getItem("username") != null) {
                        $rootScope.currentUser = sessionStorage.getItem("name");
                        $rootScope.currentRol = sessionStorage.getItem("rol");
                        return true;
                    } else {
                        return false;
                    }
                };
                
                /**
                 * @ngdoc function
                 * @name hasPermissions
                 * @methodOf mainApp.module:mainApp
                 * @description Esta función define si el usuario tiene permisos
                 * para acceder a la aplicación.
                 * @returns {Boolean} Verdadero si el usuario tiene permisos.
                 */
                $rootScope.hasPermissions = function () {
                    if (($rootScope.isAuthenticated) && (roles.indexOf(sessionStorage.getItem("rol")) > -1)) {
                        return true;
                    } else {
                        return false;
                    }
                };


                if (requireLogin && (sessionStorage.getItem("username") === null)) {
                    event.preventDefault();
                    $state.go('login', $state.params);
                }

            });

        }]);
})(window.angular);

