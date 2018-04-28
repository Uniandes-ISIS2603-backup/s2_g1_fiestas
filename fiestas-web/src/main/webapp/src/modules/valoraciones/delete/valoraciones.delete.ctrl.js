(function (ng) {
    var mod = ng.module("valoracionModule");
    mod.constant("valoracionesContext", "api/valoraciones");
    mod.controller('valoracionDeleteCtrl', ['$scope', '$http', 'valoracionesContext', '$state',
        /**
         * @ngdoc controller
         * @name valoraciones.controller:valoracionDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Autores. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} valoracionesContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Autores en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         */
        function ($scope, $http, valoracionesContext, $state) {
            var idValoracion = $state.params.valoracionId;
            /**
             * @ngdoc function
             * @name deleteValoracion
             * @methodOf valoraciones.controller:valoracionDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el autor.
             * @param {String} id El ID del autor a eliminar.
             */
            $scope.deleteValoracion = function () {
                $http.delete(valoracionesContext + '/' + idValoracion, {}).then(function (response) {
                    $state.go('valoracionesList', {valoracionId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);