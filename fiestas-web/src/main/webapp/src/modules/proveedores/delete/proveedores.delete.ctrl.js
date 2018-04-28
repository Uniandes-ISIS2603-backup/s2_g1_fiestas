(function (ng) {
    var mod = ng.module("proveedorModule");
    mod.constant("proveedoresContext", "api/proveedores");
    mod.controller('proveedorDeleteCtrl', ['$scope', '$http', 'proveedoresContext', '$state',
        /**
         * @ngdoc controller
         * @name proveedores.controller:proveedorDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Autores. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} proveedoresContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Autores en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, proveedoresContext, $state) {
            var idProveedor = $state.params.proveedorId;
            /**
             * @ngdoc function
             * @name deleteProveedor
             * @methodOf proveedores.controller:proveedorDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el autor.
             * @param {String} id El ID del autor a eliminar.
             */
            $scope.deleteProveedor = function () {
                $http.delete(proveedoresContext + '/' + idProveedor, {}).then(function (response) {
                    $state.go('proveedoresList', {proveedorId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);