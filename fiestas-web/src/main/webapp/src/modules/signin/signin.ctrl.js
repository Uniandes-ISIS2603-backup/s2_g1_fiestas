(function (ng) {
    var mod = ng.module("signinModule");
    mod.constant("clientesContext", "api/clientes");
    mod.constant("proveedoresContext", "api/proveedores");
    mod.controller('signinCtrl', ['$scope', '$http', '$state', '$rootScope', 'clientesContext', 'proveedoresContext',
        /**
         * @ngdoc controller
         * @name login.controller:loginCtrl
         * @description
         * Definición del controlador de Angular del módulo Login. 
         * Se crea el controlador con el cual se maneja el módulo.
         * En el controlador se definen los atributos y métodos que pueden
         * ser accedidos desde el HTML utilizando el $scope.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $rootScope Referencia injectada al Scope definido
         * para toda la aplicación.
         */
        function ($scope, $http, $state, $rootScope, clientesContext, proveedoresContext) {

            $scope.user = {};
            $scope.data = {};            

            /**
             * @ngdoc function
             * @name autenticar
             * @methodOf login.controller:loginCtrl
             * @description
             * Esta función procesa el inicio de sesión usando los datos del
             * $scope.
             */
            $scope.registrarCliente = function () 
            {                
                $http.post(clientesContext, $scope.data).then(function (response) {
                     $state.go('main', {clienteId: response.data.id}, {reload: true});
                });
            };
            
            $scope.registrarProveedor = function () 
            {                
                $http.post(proveedoresContext, $scope.data).then(function (response) {
                     $state.go('main', {proveedorId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);

