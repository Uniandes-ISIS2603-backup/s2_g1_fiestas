(function (ng) {
    var mod = ng.module("loginModule");
    mod.controller('logoutCtrl', ['$rootScope', '$state',
        /**
         * @ngdoc controller
         * @name login.controller:logoutCtrl
         * @description
         * Definición del controlador de cerrar sesión del módulo de Login. 
         * Se crea el controlador con el cual se maneja el módulo. Al cargarse
         * automáticamente cierra la sesión.
         * @param {Object} $rootScope Referencia injectada al Scope definido
         * para toda la aplicación.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($rootScope, $state) {
            if (sessionStorage.getItem("username")) {
                sessionStorage.clear();
            } else {
                $state.go('main', {}, {reload: true});
            }
        }
    ]);
}
)(window.angular);

