(function (ng) {
    var mod = ng.module("contratoModule");
    mod.constant("contratoContextE", "contratos");
    
    mod.controller('contratoDetailCtrl', ['$scope', '$http', 'contratoContext', '$state', '$filter',
        function ($scope, $http, contratoContextE, $state, $filter) {
            /**
             * @ngdoc controller
             * @name contratos.controller:contratoDetailCtrl
             * @description
             * Definición de un controlador auxiliar del módulo Contratos. 
             * Se crea el controlador con el cual se manejan las vistas de detalle
             * del módulo.
             * @param {Object} $scope Referencia injectada al Scope definida para este
             * controlador, el scope es el objeto que contiene las variables o 
             * funciones que se definen en este controlador y que son utilizadas 
             * desde el HTML.
             * @param {Object} $http Objeto injectado para la manejar consultas HTTP
             * @param {Object} booksContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Contratos en el Backend.
             * @param {Object} $state Dependencia injectada en la que se recibe el 
             * estado actual de la navegación definida en el módulo.
             */
            if (($state.params.contratoId !== undefined) && ($state.params.contratoId !== null)) {
                 /**
                 * @ngdoc function
                 * @name getContratoID
                 * @methodOf contratos.controller:contratoDetailCtrl
                 * @description
                 * Esta función utiliza el protocolo HTTP para obtener el recurso 
                 * donde se encuentra el autor por ID en formato JSON.
                 * @param {String} URL Dirección donde se encuentra el recurso
                 * del contrato o API donde se puede consultar.
                 */
                $http.get(contratoContextE).then(function (response) {
                    
                    $scope.contratosRecords = response.data;
                    $scope.currentContrato = $filter('filter')($scope.contratosRecords, {id: $state.params.contratoId}, true)[0];
                    $scope.horariosRecords=response.data.horarios;
                    
                });
            }
        }
    ]);
}
)(window.angular);